alter table member
    add unique unique_member_name (name);
alter table tag
    add unique unique_tag_name (name);
