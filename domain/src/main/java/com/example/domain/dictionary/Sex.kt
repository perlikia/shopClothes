package com.example.domain.dictionary

enum class Sex(val key: String, val label: String) {
    Female("female","Женский"),
    Male("male","Мужской"),
    None("none", "Отсутствует");

    companion object {
        fun toMap() : HashMap<String, String>{
            return HashMap<String, String>().apply {
                put(Female.key, Female.label)
                put(Male.key, Male.label)
                put(None.key, None.label)
            }
        }

        fun convertToSex(value: String?) : Sex{
            if(value == "female") {
                return Female
            }
            else if(value == "male"){
                return Male
            }
            return None
        }
    }
}