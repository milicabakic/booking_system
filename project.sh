#!/bin/bash

function menu() {
  echo "Enter 1 for running app or 0 for stopping app"
  read value

  case $value in
  1) docker-compose up -d;;
  0) docker stop $(docker ps -a -q);;
  *) menu;;
  esac
}

menu

