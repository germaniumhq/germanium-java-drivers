
properties([
    parameters([
        string(name: 'LOCAL_PROXY',
               defaultValue: '172.17.0.1:3128',
               description: 'Squid proxy to use for fetching resources'),
        string(name: 'DRIVERS_SOURCES_URL',
               defaultValue: 'http://192.168.0.2:10080/germanium/germanium-java-drivers.git',
               description: 'Location for the drivers sources.')
    ])
])

stage("Build Germanium Java Drivers") {
    node {
        deleteDir()

        checkout scm

        dockerBuild(file: './jenkins/Dockerfile.java8.build',
            build_args: [
                "http_proxy=http://${LOCAL_PROXY}",
                "https_proxy=http://${LOCAL_PROXY}",
                "ftp_proxy=http://${LOCAL_PROXY}"
            ],
            tags: ['germanium_drivers_java8']
        )
    }
}

def name = 'ge-drivers-java-' + getGuid()
//def name = 'ge-drivers-java-b64f4dbe-830d-4a2d-88ba-44b478b86cf0'

print "Building container with name: ${name}"

stage("Build and Test germanium-drivers") {
    node {
        dockerRun image: 'germanium_drivers_java8',
            env: [
                "DISPLAY=vnc:0",
                "SOURCES_URL=${DRIVERS_SOURCES_URL}",
            ],
            links: [
                "nexus:nexus",
                "vnc-server:vnc"
            ],
            name: name,
            privileged: true,
            command: "/scripts/test-drivers.sh"
    }
}

stage("Commit Image") {
    node {
        sh """
            docker commit ${name} ${name}
        """
    }
}

stage("Install into local Nexus") {
    input message: 'Install into local Nexus?'

    node {
        dockerRun image: name,
            links: [
                "nexus:nexus"
            ],
            remove: true,
            command: "/scripts/release-nexus.sh"
    }
}

