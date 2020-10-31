pipeline {
    agent { docker { image 'maven' } }
     tools { 
        maven 'maven' 
        // jdk 'JDK:9.0.4'
    }
    stages {
        stage('build') {
            
                 steps {
                    dir('/var/jenkins_home/workspace/\'project demo\'/projects/\'P04-eCommerce Application\'/starter_code') {
                    sh 'mvn clean'
                    }
                   // sh ' mvn "-f /var/jenkins_home/workspace/'project demo'/projects/'P04-eCommerce Application'/starter_code" clean'
                    
                        }
                   
           
        }
        stage('test') {
           
                 steps {
                      dir('/var/jenkins_home/workspace/\'project demo\'/projects/\'P04-eCommerce Application\'/starter_code') {
                      sh 'mvn test'
                      }
                    
            }
        }
    
 }
    post { 
        always { 
            echo 'I will always say Hello again!'
        }
    }
}
