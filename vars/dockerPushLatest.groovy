
def call(String imageNameParam=null){

    String imageNameLocal
    if (!imageNameParam?.trim()){
        imageNameLocal = "${imageName()}"
    }else{
        imageNameLocal = imageNameParam
    }

    if (!env.DOCKER_REGISTRY) {
        error 'Variable DOCKER_REGISTRY is not defined'
    }

    sh "docker tag ${env.DOCKER_REGISTRY}/bilderlings/${imageNameLocal}:${imageTag()} ${env.DOCKER_REGISTRY}/bilderlings/${imageNameLocal}:latest"
    sh "docker push ${env.DOCKER_REGISTRY}/bilderlings/${imageNameLocal}:latest"

}
