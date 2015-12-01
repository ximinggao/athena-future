-- phpMyAdmin SQL Dump
-- version 3.2.4
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Jan 24, 2011 at 01:28 AM
-- Server version: 5.1.41
-- PHP Version: 5.3.1


--
-- Dumping data for table `acl_sid`
--

INSERT INTO acl_sid (id, principal, sid) VALUES (1, 1, 'john');
INSERT INTO acl_sid (id, principal, sid) VALUES (2, 1, 'jane');
INSERT INTO acl_sid (id, principal, sid) VALUES (3, 1, 'mike');

--
-- Dumping data for table acl_class
--

INSERT INTO acl_class (id, class) VALUES (1, 'org.krams.tutorial.domain.AdminPost');
INSERT INTO acl_class (id, class) VALUES (2, 'org.krams.tutorial.domain.PersonalPost');
INSERT INTO acl_class (id, class) VALUES (3, 'org.krams.tutorial.domain.PublicPost');

--
-- Dumping data for table acl_object_identity
--

INSERT INTO acl_object_identity (id, object_id_class, object_id_identity, parent_object, owner_sid, entries_inheriting) VALUES (1, 1, 1, NULL, 1, 0);
INSERT INTO acl_object_identity (id, object_id_class, object_id_identity, parent_object, owner_sid, entries_inheriting) VALUES (2, 1, 2, NULL, 1, 0);
INSERT INTO acl_object_identity (id, object_id_class, object_id_identity, parent_object, owner_sid, entries_inheriting) VALUES (3, 1, 3, NULL, 1, 0);
INSERT INTO acl_object_identity (id, object_id_class, object_id_identity, parent_object, owner_sid, entries_inheriting) VALUES (4, 2, 1, NULL, 1, 0);
INSERT INTO acl_object_identity (id, object_id_class, object_id_identity, parent_object, owner_sid, entries_inheriting) VALUES (5, 2, 2, NULL, 1, 0);
INSERT INTO acl_object_identity (id, object_id_class, object_id_identity, parent_object, owner_sid, entries_inheriting) VALUES (6, 2, 3, NULL, 1, 0);
INSERT INTO acl_object_identity (id, object_id_class, object_id_identity, parent_object, owner_sid, entries_inheriting) VALUES (7, 3, 1, NULL, 1, 0);
INSERT INTO acl_object_identity (id, object_id_class, object_id_identity, parent_object, owner_sid, entries_inheriting) VALUES (8, 3, 2, NULL, 1, 0);
INSERT INTO acl_object_identity (id, object_id_class, object_id_identity, parent_object, owner_sid, entries_inheriting) VALUES (9, 3, 3, NULL, 1, 0);

--
-- Dumping data for table acl_entry
--

INSERT INTO acl_entry (id, acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) VALUES (1, 1, 1, 1, 1, 1, 1, 1);
INSERT INTO acl_entry (id, acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) VALUES (2, 2, 1, 1, 1, 1, 1, 1);
INSERT INTO acl_entry (id, acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) VALUES (3, 3, 1, 1, 1, 1, 1, 1);
INSERT INTO acl_entry (id, acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) VALUES (4, 1, 2, 1, 2, 1, 1, 1);
INSERT INTO acl_entry (id, acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) VALUES (5, 2, 2, 1, 2, 1, 1, 1);
INSERT INTO acl_entry (id, acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) VALUES (6, 3, 2, 1, 2, 1, 1, 1);
INSERT INTO acl_entry (id, acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) VALUES (7, 4, 1, 1, 1, 1, 1, 1);
INSERT INTO acl_entry (id, acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) VALUES (8, 5, 1, 1, 1, 1, 1, 1);
INSERT INTO acl_entry (id, acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) VALUES (9, 6, 1, 1, 1, 1, 1, 1);
INSERT INTO acl_entry (id, acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) VALUES (10, 7, 1, 1, 1, 1, 1, 1);
INSERT INTO acl_entry (id, acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) VALUES (11, 8, 1, 1, 1, 1, 1, 1);
INSERT INTO acl_entry (id, acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) VALUES (12, 9, 1, 1, 1, 1, 1, 1);
INSERT INTO acl_entry (id, acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) VALUES (13, 7, 2, 1, 2, 1, 1, 1);
INSERT INTO acl_entry (id, acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) VALUES (14, 8, 2, 1, 2, 1, 1, 1);
INSERT INTO acl_entry (id, acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) VALUES (15, 9, 2, 1, 2, 1, 1, 1);
INSERT INTO acl_entry (id, acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) VALUES (28, 4, 3, 2, 1, 1, 1, 1);
INSERT INTO acl_entry (id, acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) VALUES (29, 5, 3, 2, 1, 1, 1, 1);
INSERT INTO acl_entry (id, acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) VALUES (30, 6, 3, 2, 1, 1, 1, 1);
INSERT INTO acl_entry (id, acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) VALUES (31, 4, 4, 2, 2, 1, 1, 1);
INSERT INTO acl_entry (id, acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) VALUES (32, 5, 4, 2, 2, 1, 1, 1);
INSERT INTO acl_entry (id, acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) VALUES (33, 6, 4, 2, 2, 1, 1, 1);
INSERT INTO acl_entry (id, acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) VALUES (34, 7, 3, 2, 1, 1, 1, 1);
INSERT INTO acl_entry (id, acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) VALUES (35, 8, 3, 2, 1, 1, 1, 1);
INSERT INTO acl_entry (id, acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) VALUES (36, 9, 3, 2, 1, 1, 1, 1);
INSERT INTO acl_entry (id, acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) VALUES (37, 7, 4, 2, 2, 1, 1, 1);
INSERT INTO acl_entry (id, acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) VALUES (38, 8, 4, 2, 2, 1, 1, 1);
INSERT INTO acl_entry (id, acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) VALUES (39, 9, 4, 2, 2, 1, 1, 1);
INSERT INTO acl_entry (id, acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) VALUES (40, 7, 5, 3, 1, 1, 1, 1);
INSERT INTO acl_entry (id, acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) VALUES (41, 8, 5, 3, 1, 1, 1, 1);
INSERT INTO acl_entry (id, acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) VALUES (42, 9, 5, 3, 1, 1, 1, 1);

--
-- Dumping data for table admin_post
--
INSERT INTO admin_post (id, date, message) VALUES (1, '2011-01-03 21:37:58', 'Custom post #1 from admin');
INSERT INTO admin_post (id, date, message) VALUES (2, '2011-01-04 21:38:39', 'Custom post #2 from admin');
INSERT INTO admin_post (id, date, message) VALUES (3, '2011-01-05 21:39:37', 'Custom post #3 from admin');

--
-- Dumping data for table personal_post
--
INSERT INTO personal_post (id, date, message) VALUES (1, '2011-01-06 21:40:02', 'Custom post #1 from user');
INSERT INTO personal_post (id, date, message) VALUES (2, '2011-01-07 21:40:13', 'Custom post #2 from user');
INSERT INTO personal_post (id, date, message) VALUES (3, '2011-01-08 21:40:34', 'Custom post #3 from user');

--
-- Dumping data for table public_post
--
INSERT INTO public_post (id, date, message) VALUES (1, '2011-01-10 21:40:44', 'Custom post #1 from public');
INSERT INTO public_post (id, date, message) VALUES (2, '2011-01-11 21:40:48', 'Custom post #2 from public');
INSERT INTO public_post (id, date, message) VALUES (3, '2011-01-12 21:41:08', 'Custom post #3 from public');
