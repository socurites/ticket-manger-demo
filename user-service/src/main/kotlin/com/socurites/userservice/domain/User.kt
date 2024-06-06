package com.socurites.userservice.domain

import jakarta.persistence.*

@Entity
@Table(name = "users")
class User (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column
    val email: String,

    @Column
    val password: String,

    @Column
    val username: String,

    @Column
    val profileUrl: String? = null,
): BaseEntity()