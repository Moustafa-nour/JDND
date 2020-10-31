pipeline {
    agent { docker { image 'maven' } }
     tools { 
        maven 'maven' 
    }
    
    stages {
        stage('build') {
                 steps {
                    sh ' mvn -f /var/jenkins_home/workspace/\'project demo\'/projects/\'P04-eCommerce Application\'/starter_code clean package'
                        }
        }
        stage('test') {
                 steps {
                     sh 'mvn -f /var/jenkins_home/workspace/\'project demo\'/projects/\'P04-eCommerce Application\'/starter_code test'
                      }
        }
    
 }
    post { 
        always { 
            echo 'I will always say Hello again!'
        }
    }
}
