/*
Run this script to create indexes for all entities
*/

db.bookmarks.ensureIndex({ user_id : 1})
db.bookmarks.ensureIndex({ created_on : -1})
db.bookmarks.ensureIndex({ modified_at : -1})
db.bookmarks.ensureIndex({ is_deleted : 1 })

db.notes.ensureIndex({ user_id : 1 })
db.notes.ensureIndex({ created_on : -1})
db.notes.ensureIndex({ modified_at : -1})
db.notes.ensureIndex({ is_deleted : 1 })

db.pins.ensureIndex({ user_id : 1 })
db.pins.ensureIndex({ created_on : -1})
db.pins.ensureIndex({ modified_at : -1})
db.pins.ensureIndex({ is_deleted : 1 })

db.activities.ensureIndex({ user_id : 1 })
db.activities.ensureIndex({ created_on : -1})