
stage('Build Drivers') {
    node {
        deleteDir()

        checkout scm

        dockerBuild file: './Dockerfile',
            env: [
                "DISPLAY=vnc:0"
            ],
            network: 'host',
            tags: ['germanium_drivers:java']
    }
}

