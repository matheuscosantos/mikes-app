variable "region" {
  type    = string
  default = "us-east-2"
}

variable "name" {
  type    = string
  default = "mikes"
}

variable "subnets" {
  type    = list(string)
  default = [
    "subnet-0bf429bd21d7c0dfb"
  ]
}
