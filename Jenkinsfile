
properties([
    safeParameters(this, [
        string(name: 'IMAGE_NAME', defaultValue: '',
                description: 'Container image name. By default it is ge-drivers-<uid>'),
        booleanParam(name: 'RUN_FIREFOX_TESTS', defaultValue: true,
                description: 'Should the firefox tests run'),
        booleanParam(name: 'RUN_CHROME_TESTS', defaultValue: true,
                description: 'Should the chrome tests run')
    ]),

    pipelineTriggers([
        upstream(
            threshold: 'SUCCESS',
            upstreamProjects: '/build-system/germaniumhq-java-build-system/master'
        )
    ])
])

safeParametersCheck(this)

stage("Build Germanium Drivers") {
    parallel 'Java 8': {
        node {
            deleteDir()
            checkout scm

            versionManager("-l ./version_values.yml")

            docker.build('germanium_drivers_java8',
                         '-f Dockerfile .')
        }
    }
}

// -------------------------------------------------------------------
// container name definition
// -------------------------------------------------------------------
def name
if (params.IMAGE_NAME) {
    name = 'ge-java-drivers-' + params.IMAGE_NAME
} else {
    name = 'ge-java-drivers-' + getGuid()
}

println "Building container with name: ${name}"

stage("Test germanium-drivers") {
    parallel 'Java 8 Tests': {
        node {
            dockerRm containers: [name]
            dockerInside image: 'germanium_drivers_java8',
                env: [
                    "DISPLAY=vnc:0"
                ],
                links: [
                    "vnc-server:vnc"
                ],
                name: name,
                privileged: true,
                volumes: [
                    '/dev/shm:/dev/shm:rw'
                ],
                code: {
                    junitReports("/src/target/surefire-reports") {
                        sh """
                            cd /src
                            . bin/prepare_firefox.sh
                            mvn test
                        """
                    }
                }
        }
    }
}

stage("Install into local Nexus") {
    input message: 'Install into local Nexus?'

    node {
        dockerInside image: name,
            links: [
                'nexus:nexus'
            ],
            code: {
                withCredentials([file(credentialsId: 'NEXUS_SETTINGS_XML',
                                      variable: 'NEXUS_SETTINGS_XML')]) {
                    sh """
                        cp ${env.NEXUS_SETTINGS_XML} /germanium/.m2/settings.xml
                        chmod 666 /germanium/.m2/settings.xml

                        /src/bin/release_nexus.sh
                    """
                }
            }
    }
}

stage("Install into global PyPI") {
    input message: 'Install into global PyPI?'

    node {
        dockerInside image: name,
            code: {
                sh "/src/bin/release.sh"
            }
    }
}

