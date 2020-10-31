pipeline {
    agent  any
     tools { 
        maven 'maven' 
    }
    
    stages {
        stage('build') {
                 steps {
                    sh ' mvn -f /var/jenkins_home/workspace/\'JNDN pipeline demo\'/projects/\'P04-eCommerce Application\'/starter_code clean package'
                        }
        }
        stage('test') {
                 steps {
                     sh 'mvn -f /var/jenkins_home/workspace/\'JNDN pipeline demo\'/projects/\'P04-eCommerce Application\'/starter_code clean test'
                      }
        }
    
 }
    post { 
        always { 
            echo 'I will always say Hello again!'
        }
    }
}
