def call(List containers) {
    echo 'Removing all containers'
    containers.each {
        echo "Removing ${it}"
        sh "docker stop ${it}"
        sh "docker rm -v ${it}"
        sh "docker rmi -f ${imageName}"
    }
}

