package com.socurites.userservice.domain

import jakarta.persistence.*

@Entity
@Table(name = "users")
data class User (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column
    var email: String,

    @Column
    var password: String,

    @Column
    var username: String,

    @Column
    var profileUrl: String? = null,
): BaseEntity()