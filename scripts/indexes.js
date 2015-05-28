/*
Run this script to create indexes for all entities
*/

db.bookmarks.ensureIndex({ user_id : 1})
db.bookmarks.ensureIndex({ created_on : -1})
db.bookmarks.ensureIndex({ modified_at : -1})