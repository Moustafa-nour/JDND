pipeline {
    agent { docker { image 'maven' } }
     tools { 
        maven 'maven' 
    }
    
    stages {
        stage('build') {
                 steps {
                    sh ' mvn -f /var/jenkins_home/workspace/\'project demo\'/projects/\'P04-eCommerce Application\'/starter_code clean'
                        }
        }
        stage('test') {
                 steps {
                     sh 'mvn -f /var/jenkins_home/workspace/\'project demo\'/projects/\'P04-eCommerce Application\'/starter_code clean'
                      }
        }
    
 }
    post { 
        always { 
            echo 'I will always say Hello again!'
        }
    }
}
