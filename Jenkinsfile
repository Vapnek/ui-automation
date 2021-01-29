pipeline{
    agent any
    triggers {
        pollSCM('*/1 * * * *')
    }
    stages
    {
        stage('build'){
            steps {
                echo "Building application"
                sh "sleep 5"
            }
        }
        stage('deploy-staging') {
            steps {
                script{
                    deploy("staging")
                }
            }
        }
        stage('test-staging') {
            steps {
                script{
                    test("staging")
                }
            }
        }
        stage('deploy-production') {
            steps {
                script{
                    deploy("production")
                }
            }
        }
        stage('test-production') {
            steps {
                script{
                    test("production")
                }
            }
        }
    }
}


def deploy(String environment){
    echo "Deployment to ${environment} in progress"
    try{
        build job: "ui-tests", parameters: [string(name: "ENVIRONMENT", value: "${environment}")]
        echo "bash send_notification.sh '${environment} deployment' 0"
    }  
    catch(Exception e)
    {
        sh "bash send_notification.sh '${environment} deployment' 1"
    }
}

def test(String environment){
    echo "Running tests on ${environment}"
     sh "docker stop firefox"
        sh "docker stop chrome"
        sh "docker stop selenium_hub"
        sh "docker rm firefox"
        sh "docker rm chrome"
        sh "docker rm selenium_hub"
    try{
        sh "docker run --net test-automation-setup -d -t -p 4444:4444 --name selenium_hub selenium/hub"
        sh "docker run --net test-automation-setup -d -t --name chrome -e HUB_PORT_4444_TCP_ADDR=selenium_hub \
       -e HUB_PORT_4444_TCP_PORT=4444 -e NODE_MAX_SESSION=1 -e NODE_MAX_INSTANCES=1 -v /dev/shm:/dev/shm selenium/node-chrome"
        sh "docker run --net test-automation-setup -d -t --name firefox -e HUB_PORT_4444_TCP_ADDR=selenium_hub \
       -e HUB_PORT_4444_TCP_PORT=4444 -e NODE_MAX_SESSION=1 -e NODE_MAX_INSTANCES=1 -v /dev/shm:/dev/shm selenium/node-firefox"
        sh "docker run --net test-automation-setup -d -t --name mvn_tests_${environment} \
        mvn clean test -Dbrowser=chrome -DgridURL=selenium_hub:4444"
        sh "bash send_notification.sh 'Testing on ${environment}' 0"
    }
    catch(Exception e)
    {
      sh "bash send_notification.sh '${environment} deployment' 1"
    }
    finally{
        sh "docker stop mvn_tests_${environment}"
        sh "docker stop firefox"
        sh "docker stop chrome"
        sh "docker stop selenium_hub"
        sh "docker rm mvn_tests_${environment}"
        sh "docker rm firefox"
        sh "docker rm chrome"
        sh "docker rm selenium_hub"
}
}