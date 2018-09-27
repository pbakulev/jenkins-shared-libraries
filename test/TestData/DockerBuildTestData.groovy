package TestData

static Map commonVariables(){ [
        JOB_NAME: 'Job_Name',
        BRANCH_NAME: 'master',
        BUILD_ID: '1',
        DOCKER_REGISTRY: 'registry.com'
    ]
}

static correctPaths(){
    ['some_path', './some_path', '/home/some_path', '.', '\\\\']
}

static incorrectPaths(){
    [null, '', ' ']
}