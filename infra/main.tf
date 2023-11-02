provider "aws" {
  region = var.region
}

# -- iam

resource "aws_iam_role" "ecs_execution_role" {
  name = "${var.name}_ecs_execution_role"
  assume_role_policy = file("iam/role/ecs_execution_role.json")
}

# -- task definition

data "aws_db_instance" "db_instance" {
  db_instance_identifier = "mikes-db"
}

data "aws_secretsmanager_secret" "db_credentials" {
  name = "mikes/db/db_credentials"
}

data "aws_secretsmanager_secret_version" "db_credentials_current" {
  secret_id = data.aws_secretsmanager_secret.db_credentials.id
}

locals {
  db_credentials = jsondecode(data.aws_secretsmanager_secret_version.db_credentials_current.secret_string)
}

resource "aws_ecs_task_definition" "mikes_app_task_definition" {
  family                   = var.name
  network_mode             = "awsvpc"
  requires_compatibilities = ["EC2"]

  execution_role_arn = aws_iam_role.ecs_execution_role.arn

  container_definitions = templatefile("container/definitions/mikes_app_container_definitions.json", {
    DB_HOST     = data.aws_db_instance.db_instance.address
    DB_PORT     = data.aws_db_instance.db_instance.port
    DB_USER     = local.db_credentials["username"]
    DB_PASSWORD = local.db_credentials["password"]
  })
}

# -- service

data "aws_ecs_cluster" "ecs_cluster" {
  cluster_name = "${var.name}_cluster"
}

resource "aws_ecs_service" "mikes_app_service" {
  name            = "${var.name}_service"
  cluster         = data.aws_ecs_cluster.ecs_cluster.id
  task_definition = aws_ecs_task_definition.mikes_app_task_definition.arn
  desired_count = 1

  network_configuration {
    subnets = var.subnets
  }
}
