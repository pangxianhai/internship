#!/bin/bash

function build_internship()
{
    cd ~/code/java/internship/internship
    mvn_tools.sh install
}

function build_intership_web()
{
    #build_static
    cd ~/code/java/internship/internship-web
    . tomcat.sh stop internship-web
    mvn -Pdev clean package
    rm -rf $base/webapps/*
    rm -rf $base/work/*
    rm -rf $base/logs/*
    cp target/*.war $base/webapps/ROOT.war
    . tomcat.sh start internship-web
}

function build_pro(){
    cd ~/code/java/internship/internship-web
    mvn -Ppro clean package
    cp target/*.war ../ROOT.war
}

function build_static()
{
    cd ~/code/java/internship/internship-static
    rm -rf ../pro/*
    rm -rf ~/code/java/internship/internship-web/src/main/webapp/static
    rm -rf ~/code/java/internship/internship-web/src/main/webapp/WEB-INF/views
    if [ "x$1" = "xdev" ]
    then
        cp -r ./dev/* ~/www/internship-web/webapps/ROOT/
        cp -r ./dev/* ~/code/java/internship/internship-web/src/main/webapp/
        rm ~/code/java/internship/internship-web/src/main/webapp/coolie.config.js
        rm ~/code/java/internship/internship-web/src/main/webapp/coolie-map.json 
        rm ~/code/java/internship/internship-web/src/main/webapp/coolie.js
        rm ~/code/java/internship/internship-web/src/main/webapp/intership-static.iml
    else
	cd dev/
	~/node_modules/coolie/bin/coolie  build
	cd ../
        cp -r ./pro/* ~/www/internship-web/webapps/ROOT/
        cp -r ./pro/* ~/code/java/internship/internship-web/src/main/webapp/
    fi
}

function usage()
{
    echo 'usage: [all|jar|web|static|pro]'
}

if [ "x$1" = "x" ]
then
    usage
elif [ $1 = 'web' ]
then
    build_intership_web
elif [ $1 = 'jar' ]
then
    build_internship
elif [ $1 = 'all' ]
then
    build_internship
    build_intership_web
elif [ $1 = 'static' ]
then
    build_static $2
elif [ $1 = 'pro' ]
then
    build_pro
else
    usage
fi
