properties([
    parameters([
        booleanParam(name: 'CLEAR_DOCKER_CACHE', defaultValue: false,
                description: 'Run without the docker cache.'),
    ])
])

CLEAR_DOCKER_CACHE = Boolean.valueOf(CLEAR_DOCKER_CACHE)

stage('Build Drivers') {
    node {
        deleteDir()

        checkout scm

        dockerBuild file: './Dockerfile',
            networks: ['vnc'],
            no_cache: CLEAR_DOCKER_CACHE,
            tags: ['germanium_drivers:java']

        dockerRun image: 'germanium_drivers:java',
            privileged: true,
            remove: true,
            env: [
                'DISPLAY=vnc-server:0'
            ],
            links: [
                'vnc-server:vnc-server'
            ],
            volumes: [
                '/dev/shm:/dev/shm:rw'
            ]
    }
}

