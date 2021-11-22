package com.example.demo.service.impl;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.example.demo.entity.MUser;

public class SimpleLoginUser extends org.springframework.security.core.userdetails.User {
  // DBより検索したMUserエンティティ
  // アプリケーションから利用されるのでフィールドに定義
  private MUser user;

  /**
   * データベースより検索したuserエンティティよりSpring Securityで使用するユーザー認証情報の
   * インスタンスを生成する
   *
   * @param user userエンティティ
   */
  public SimpleLoginUser(MUser user) {
    super(user.getEmail(), user.getPassword(), convertGrantedAuthorities(user.getRole()));
    this.user = user;
  }

  public MUser getUser() {
    return user;
  }

  /**
   * カンマ区切りのロールをSimpleGrantedAuthorityのコレクションへ変換する
   *
   * @param roles カンマ区切りのロール
   * @return SimpleGrantedAuthorityのコレクション
   */
  static Set<GrantedAuthority> convertGrantedAuthorities(String roles) {
    if (roles == null || roles.isEmpty()) {
      return Collections.emptySet();
    }
    Set<GrantedAuthority> authorities = Stream.of(roles.split(","))
        .map(SimpleGrantedAuthority::new)
        .collect(Collectors.toSet());
    return authorities;
  }

}