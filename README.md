# XspeedIt

XspeedIt est une société d'import / export ayant robotisé toute sa chaîne d'emballage de colis.
Elle souhaite trouver un algorithme permettant à ses robots d'optimiser le nombre de cartons d'emballage utilisés.

Les articles à emballer sont de taille variable, représentée par un entier compris entre 1 et 9.
Chaque carton a une capacité de contenance de 10.
Ainsi, un carton peut par exemple contenir un article de taille 3, un article de taille 1, et un article de taille 6.

La chaîne d'articles à emballer est représentée par une suite de chiffres, chacun représentant un article par sa taille.
Après traitement par le robot d'emballage, la chaîne est séparée par des "/" pour représenter les articles contenus dans un carton.

L'application se présente sous la forme d'une API pouvant manipuler des chaînes d'emballage. 
Chacune de ces chaînes étant composées d'articles et de boîtes

## Bien démarrer

### Prérequis

L'application nécessite Java 1.8 minimum

### Installation

* Récupérer le fichier xspeedit-1.0.jar à la racine du projet
* Exécuter la commande suivante pour démarrer l'application
  ```shell
  java -jar xspeedit-1.0.jar
  ```
* L'api est disponible par défaut sur le port 8080

### Utilisation

Il suffit de créer une chaîne dans l'API pour voir  le résultat de l'emballage calculé

Par exemple
```shell
curl -X POST http://localhost:8080/chains -d '{"articles": "36153417531"}' -H 'Content-Type: application/json'
```

Retourne
```
{
  "id": 1,
  "articles": [...],
  "boxes": [...],
  "initialization":"36153417531",
  "packaging": "73/64/55/33111",
  "_links": {
    "self": {
      "href": "http://localhost:8080/chains/1"
    },
    "chains": {
      "href": "http://localhost:8080/chains"
    }
  }
}
```

La propriété initialization rappelle la liste des articles avec laquelle la chaîne a été créée.
La propriété packaging renvoie la répartition des articles dans les boîtes.

Les propriétés articles et boxes contiennent ces informatiosn sous forme d'objets.

## Méthodes

### Créer une chaîne

Crée une chaîne avec des articles et les répartit dans des boîtes

* **URL**: /chains
* **Méthode**: POST
* **Paramètres dans le body**:
  - _articles_ - chaîne de chiffre non nuls représentant la liste des articles `{"articles": "36153417531"}`
* **Format de retour**: un objet JSON représentant la chaîne, avec les propriété suivantes
  - _id_ - id de la chaîne
  - _articles_ - liste d'objets JSON représentant les articles
  - _boxes_ - liste d'objets JSON représentant les boîtes
  - _initialization_ - liste des articles sous forme de chaîne de caractères
  - _packaging_ - répartition des articles dans les boîtes sous forme de chaîne de caractères
  - _\_links_ - liens hypermédia renvoyant sur la chaîne elle-même ou la liste des chaînes
* **Erreur**
  - **400 Bad Request** - Il manque un ou plusieurs des paramètres requis ou certains paramètres n'ont pas une forme valide.
* **Exemple**

  Requête
  ```shell
  curl -X POST http://localhost:8080/chains -d '{"articles": "36153417531"}' -H 'Content-Type: application/json'
  ```
  
  Réponse
  ```json
  {
    "id": 1,
    "articles": [
      {
        "id": 1,
        "size": 3
      },
      {
        "id": 2,
        "size": 6
      },
      {
        "id": 3,
        "size": 1
      },
      {
        "id": 4,
        "size": 5
      },
      {
        "id": 5,
        "size": 3
      },
      {
        "id": 6,
        "size": 4
      },
      {
        "id": 7,
        "size": 1
      },
      {
        "id": 8,
        "size": 7
      },
      {
        "id": 9,
        "size": 5
      },
      {
        "id": 10,
        "size": 3
      },
      {
        "id": 11,
        "size": 1
      }
    ],
    "boxes": [
      {
        "id": 1,
        "articles": [
          {
            "id": 8,
            "size": 7
          },
          {
            "id": 1,
            "size": 3
          }
        ],
        "freeSpace": 0,
        "articlesAsString": "73"
      },
      {
        "id": 2,
        "articles": [
          {
            "id": 2,
            "size": 6
          },
          {
            "id": 6,
            "size": 4
          }
        ],
        "freeSpace": 0,
        "articlesAsString": "64"
      },
      {
        "id": 3,
        "articles": [
          {
            "id": 4,
            "size": 5
          },
          {
            "id": 9,
            "size": 5
          }
        ],
        "freeSpace": 0,
        "articlesAsString": "55"
      },
      {
        "id": 4,
        "articles": [
          {
            "id": 5,
            "size": 3
          },
          {
            "id": 10,
            "size": 3
          },
          {
            "id": 3,
            "size": 1
          },
          {
            "id": 7,
            "size": 1
          },
          {
            "id": 11,
            "size": 1
          }
        ],
        "freeSpace": 1,
        "articlesAsString": "33111"
      }
    ],
    "initialization": "36153417531",
    "packaging": "73/64/55/33111",
    "_links": {
      "self": {
        "href": "http://localhost:8080/chains/1"
      },
      "chains": {
        "href": "http://localhost:8080/chains"
      }
    }
  }
  ```
  
  ### Afficher une chaîne

Affiche une chaîne spécifique

* **URL**: /chains/:id
* **Méthode**: GET
* **Paramètres de la requête**:
  - _id (requis)_ - id de la chaîne à afficher
* **Format de retour**: un objet JSON représentant la chaîne, avec les propriété suivantes
  - _id_ - id de la chaîne
  - _articles_ - liste d'objets JSON représentant les articles
  - _boxes_ - liste d'objets JSON représentant les boîtes
  - _initialization_ - liste des articles sous forme de chaîne de caractères
  - _packaging_ - répartition des articles dans les boîtes sous forme de chaîne de caractères
  - _\_links_ - liens hypermédia renvoyant sur la chaîne elle-même ou la liste des chaînes
* **Erreur**
  - **404 Not Found** - Il n'existe pas de chaîne ayant l'id demandé.
* **Exemple**

  Requête
  ```shell
  curl http://localhost:8080/chains/1
  ```
  
  Réponse
  ```json
  {
    "id": 1,
    "articles": [
      {
        "id": 1,
        "size": 3
      },
      {
        "id": 2,
        "size": 6
      },
      {
        "id": 3,
        "size": 1
      },
      {
        "id": 4,
        "size": 5
      },
      {
        "id": 5,
        "size": 3
      },
      {
        "id": 6,
        "size": 4
      },
      {
        "id": 7,
        "size": 1
      },
      {
        "id": 8,
        "size": 7
      },
      {
        "id": 9,
        "size": 5
      },
      {
        "id": 10,
        "size": 3
      },
      {
        "id": 11,
        "size": 1
      }
    ],
    "boxes": [
      {
        "id": 1,
        "articles": [
          {
            "id": 8,
            "size": 7
          },
          {
            "id": 1,
            "size": 3
          }
        ],
        "freeSpace": 0,
        "articlesAsString": "73"
      },
      {
        "id": 2,
        "articles": [
          {
            "id": 2,
            "size": 6
          },
          {
            "id": 6,
            "size": 4
          }
        ],
        "freeSpace": 0,
        "articlesAsString": "64"
      },
      {
        "id": 3,
        "articles": [
          {
            "id": 4,
            "size": 5
          },
          {
            "id": 9,
            "size": 5
          }
        ],
        "freeSpace": 0,
        "articlesAsString": "55"
      },
      {
        "id": 4,
        "articles": [
          {
            "id": 5,
            "size": 3
          },
          {
            "id": 10,
            "size": 3
          },
          {
            "id": 3,
            "size": 1
          },
          {
            "id": 7,
            "size": 1
          },
          {
            "id": 11,
            "size": 1
          }
        ],
        "freeSpace": 1,
        "articlesAsString": "33111"
      }
    ],
    "initialization": "36153417531",
    "packaging": "73/64/55/33111",
    "_links": {
      "self": {
        "href": "http://localhost:8080/chains/1"
      },
      "chains": {
        "href": "http://localhost:8080/chains"
      }
    }
  }
  ```
  
  ### Afficher toutes les chaînes

Affiche toutes chaînes existantes

* **URL**: /chains
* **Méthode**: GET
* **Format de retour**: une liste d'objet JSON représentant les chaînes, avec les propriété suivantes
  - _\_embedded_ - contient un objet chainList reprséentant une liste de chaînes
  - _\_links_ - liens hypermédia renvoyant sur la liste des chaînes
* **Exemple**

  Requête
  ```shell
  curl http://localhost:8080/chains
  ```
  
  Réponse
  ```json
  {
  "_embedded": {
    "chainList": [
      {
        "id": 1,
        "articles": [
          {
            "id": 1,
            "size": 3
          },
          {
            "id": 2,
            "size": 6
          },
          {
            "id": 3,
            "size": 1
          },
          {
            "id": 4,
            "size": 5
          },
          {
            "id": 5,
            "size": 3
          },
          {
            "id": 6,
            "size": 4
          },
          {
            "id": 7,
            "size": 1
          },
          {
            "id": 8,
            "size": 7
          },
          {
            "id": 9,
            "size": 5
          },
          {
            "id": 10,
            "size": 3
          },
          {
            "id": 11,
            "size": 1
          }
        ],
        "boxes": [
          {
            "id": 1,
            "articles": [
              {
                "id": 8,
                "size": 7
              },
              {
                "id": 1,
                "size": 3
              }
            ],
            "freeSpace": 0,
            "articlesAsString": "73"
          },
          {
            "id": 2,
            "articles": [
              {
                "id": 2,
                "size": 6
              },
              {
                "id": 6,
                "size": 4
              }
            ],
            "freeSpace": 0,
            "articlesAsString": "64"
          },
          {
            "id": 3,
            "articles": [
              {
                "id": 4,
                "size": 5
              },
              {
                "id": 9,
                "size": 5
              }
            ],
            "freeSpace": 0,
            "articlesAsString": "55"
          },
          {
            "id": 4,
            "articles": [
              {
                "id": 5,
                "size": 3
              },
              {
                "id": 10,
                "size": 3
              },
              {
                "id": 3,
                "size": 1
              },
              {
                "id": 7,
                "size": 1
              },
              {
                "id": 11,
                "size": 1
              }
            ],
            "freeSpace": 1,
            "articlesAsString": "33111"
          }
        ],
        "initialization": "36153417531",
        "packaging": "73/64/55/33111",
        "_links": {
          "self": {
            "href": "http://localhost:8080/chains/1"
          },
          "chains": {
            "href": "http://localhost:8080/chains"
          }
        }
      },
      {
        "id": 2,
        "articles": [
          {
            "id": 12,
            "size": 1
          },
          {
            "id": 13,
            "size": 2
          },
          {
            "id": 14,
            "size": 3
          },
          {
            "id": 15,
            "size": 4
          },
          {
            "id": 16,
            "size": 5
          },
          {
            "id": 17,
            "size": 6
          }
        ],
        "boxes": [
          {
            "id": 5,
            "articles": [
              {
                "id": 17,
                "size": 6
              },
              {
                "id": 15,
                "size": 4
              }
            ],
            "freeSpace": 0,
            "articlesAsString": "64"
          },
          {
            "id": 6,
            "articles": [
              {
                "id": 16,
                "size": 5
              },
              {
                "id": 14,
                "size": 3
              },
              {
                "id": 13,
                "size": 2
              }
            ],
            "freeSpace": 0,
            "articlesAsString": "532"
          },
          {
            "id": 7,
            "articles": [
              {
                "id": 12,
                "size": 1
              }
            ],
            "freeSpace": 9,
            "articlesAsString": "1"
          }
        ],
        "initialization": "123456",
        "packaging": "64/532/1",
        "_links": {
          "self": {
            "href": "http://localhost:8080/chains/2"
          },
          "chains": {
            "href": "http://localhost:8080/chains"
          }
        }
      }
    ]
  },
  "_links": {
    "self": {
      "href": "http://localhost:8080/chains"
    }
  }
}
  ```
