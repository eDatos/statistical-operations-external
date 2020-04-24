#!/bin/bash

HOME_PATH=statistical-operations-external
TRANSFER_PATH=$HOME_PATH/tmp
DEMO_ENV=$HOME_PATH/env
DEPLOY_TARGET_PATH_EXTERNAL=/servers/edatos-external/tomcats/edatos-external01/webapps
DEPLOY_TARGET_PATH_INTERNAL=/servers/edatos-internal/tomcats/edatos-internal01/webapps
DATA_RELATIVE_PATH_FILE=WEB-INF/classes/config/data-location.properties
LOGBACK_RELATIVE_PATH_FILE=WEB-INF/classes/logback.xml

RESTART=1

if [ "$1" == "--no-restart" ]; then
    RESTART=0
fi

scp -o ProxyCommand="ssh -W %h:%p deploy@estadisticas.arte-consultores.com" -r etc/deploy deploy@192.168.10.16:$TRANSFER_PATH
scp -o ProxyCommand="ssh -W %h:%p deploy@estadisticas.arte-consultores.com" target/statistical-operations-external-*.war deploy@192.168.10.16:$TRANSFER_PATH/statistical-operations-external.war
ssh -o ProxyCommand="ssh -W %h:%p deploy@estadisticas.arte-consultores.com" deploy@192.168.10.16 <<EOF

    chmod a+x $TRANSFER_PATH/deploy/*.sh;
    . $TRANSFER_PATH/deploy/utilities.sh
    
    ###
    # Statistical Operations External - Internal
    ###

    if [ $RESTART -eq 1 ]; then
        sudo service edatos-internal01 stop
        checkPROC "edatos-internal"
    fi

    # Update Process
    sudo rm -rf $DEPLOY_TARGET_PATH_INTERNAL/operations-internal
    sudo cp $TRANSFER_PATH/statistical-operations-external.war $DEPLOY_TARGET_PATH_INTERNAL/statistical-operations-external.war
    sudo unzip $DEPLOY_TARGET_PATH_INTERNAL/statistical-operations-external.war -d $DEPLOY_TARGET_PATH_INTERNAL/operations-internal
    sudo rm -rf $DEPLOY_TARGET_PATH_INTERNAL/statistical-operations-external.war

    # Restore Configuration
    sudo cp $DEMO_ENV/logback_internal.xml $DEPLOY_TARGET_PATH_INTERNAL/operations-internal/$LOGBACK_RELATIVE_PATH_FILE
    sudo rm -f $DEPLOY_TARGET_PATH_INTERNAL/operations-internal/WEB-INF/classes/config/application-env.yml
    sudo cp $DEMO_ENV/data-location_internal.properties $DEPLOY_TARGET_PATH_INTERNAL/operations-internal/$DATA_RELATIVE_PATH_FILE
    
    if [ $RESTART -eq 1 ]; then
        sudo chown -R edatos-internal.edatos-internal /servers/edatos-internal     
        sudo service edatos-internal01 start
    fi
    
    ###
    # Statistical Operations External - External
    ###
    
    if [ $RESTART -eq 1 ]; then
        sudo service edatos-external01 stop
        checkPROC "edatos-external"
    fi

    # Update Process
    sudo rm -rf $DEPLOY_TARGET_PATH_EXTERNAL/statistical-operations-external
    sudo mv $TRANSFER_PATH/statistical-operations-external.war $DEPLOY_TARGET_PATH_EXTERNAL/statistical-operations-external.war
    sudo unzip $DEPLOY_TARGET_PATH_EXTERNAL/statistical-operations-external.war -d $DEPLOY_TARGET_PATH_EXTERNAL/statistical-operations-external
    sudo rm -rf $DEPLOY_TARGET_PATH_EXTERNAL/statistical-operations-external.war

    # Restore Configuration
    sudo cp $DEMO_ENV/logback_external.xml $DEPLOY_TARGET_PATH_EXTERNAL/statistical-operations-external/$LOGBACK_RELATIVE_PATH_FILE
    sudo rm -f $DEPLOY_TARGET_PATH_EXTERNAL/statistical-operations-external/WEB-INF/classes/config/application-env.yml
    sudo cp $DEMO_ENV/data-location_external.properties $DEPLOY_TARGET_PATH_EXTERNAL/statistical-operations-external/$DATA_RELATIVE_PATH_FILE
    
    if [ $RESTART -eq 1 ]; then
        sudo chown -R edatos-external.edatos-external /servers/edatos-external        
        sudo service edatos-external01 start
    fi
    
    echo "Finished deploy"

EOF