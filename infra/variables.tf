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
    "subnet-0bf429bd21d7c0dfb", // a
    "subnet-051f580c310c2bc67", // b
    "subnet-0a912569f0ff495e6"  // c
  ]
}
