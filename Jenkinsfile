pipeline{
    agent any
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
     post {
                always {
                        echo "This is always section"
                       }
                success {
                        sh "bash send_notification.sh '${environment} deployment' 0"
                        }
                failure {
                        sh "bash send_notification.sh '${environment} deployment' 1"
                        }
             }
}


def deploy(String environment){
    echo "Deployment to ${environment} in progress"
    build job: "test_automation_solution", parameters: [string(name: "ENVIRONMENT", value: "${environment}")]
    
}

def test(String environment){
    echo "Running tests on ${environment}"
    try{
        sh "docker run --net test-automation-setup -d  -p 4444:4444 --name selenium_hub selenium/hub"
        sh "docker run --net test-automation-setup -d --name chrome -e HUB_PORT_4444_TCP_ADDR=selenium_hub \
       -e HUB_PORT_4444_TCP_PORT=4444 -e NODE_MAX_SESSION=2 -e NODE_MAX_INSTANCES=2 -v /dev/shm:/dev/shm selenium/node-chrome"
        sh "docker run --net test-automation-setup -d --name firefox -e HUB_PORT_4444_TCP_ADDR=selenium_hub \
       -e HUB_PORT_4444_TCP_PORT=4444 -e NODE_MAX_SESSION=2 -e NODE_MAX_INSTANCES=2 -v /dev/shm:/dev/shm selenium/node-firefox"
        sh "docker run --net test-automation-setup -d --name mvn_tests_${environment} \
        -v $PWD/test-output:/docker/test-output vapnek/mvn_tests \
        mvn clean test -Dbrowser=chrome -DgridURL=selenium_hub:4444 && mvn io.qameta.allure:allure-maven:report && rm -rf test-output/* && cp -r target/site/allure-maven-plugin test-output"
        sh "bash send_notification.sh 'Testing on ${environment}' 0"
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