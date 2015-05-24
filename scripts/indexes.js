/*
Run this script to create indexes for all entities
*/

db.bookmarks.ensureIndex({ userId : 1})
db.bookmarks.ensureIndex({ createdOn : -1})
db.bookmarks.ensureIndex({ modifiedAt : -1})