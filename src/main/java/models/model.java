package models;

import domain.UserQuery;

interface Model {
    String answer(UserQuery query);
}