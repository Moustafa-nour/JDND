pipeline {
    agent  any
     tools { 
        maven 'maven' 
    }
    
    stages {
        stage('build') {
                 steps {
                            mvn -f /Users/MOUSTAFA/.jenkins/workspace/\'project demo 2\'/projects/\'P04-eCommerce Application\'/starter_code clean packag
                     //   sh ' mvn -f /var/jenkins_home/workspace/\'JNDN pipeline demo\'/projects/\'P04-eCommerce Application\'/starter_code clean package'
                        }
        }
        stage('test') {
                 steps {
                      mvn -f /Users/MOUSTAFA/.jenkins/workspace/\'project demo 2\'/projects/\'P04-eCommerce Application\'/starter_code clean test
                     // sh 'mvn -f /var/jenkins_home/workspace/\'JNDN pipeline demo\'/projects/\'P04-eCommerce Application\'/starter_code clean test'
                      }
        }
    
 }
    post { 
        always { 
            echo 'I will always say Hello again!'
        }
    }
}
