(function($, window, document){

    "use strict"

    var TODO_URL_ROOT = '/iPersonal/dashboard/todos';

    var Todo = Base.extend({
        urlRoot: TODO_URL_ROOT,

        initialize: function() {
            this.mandatory = {
                'title': true
            },
            this.maxLength = {
                'title': 50
            },
            this.formAttributes = ['title'];
        }
    });

    var Todos = Backbone.Collection.extend({
        model: Todo
    });

    var Task = Base.extend({
        initialize: function() {
            this.mandatory = {
                'name': true,
                'task': true
            },
            this.maxLength = {
                'name': 50,
                'task': 300
            },
            this.formAttributes = ['name', 'task'];
        }
    });

    var Tasks = Backbone.Collection.extend({
        model: Task
    });

    var TodoView = BaseView.extend({

        entityType: 'TODO',
        el: $('#todo-wrapper'),
        upsertTemplate: $('#todo-upsert-template').html(),
        displayTemplate: $('#todo-display-template').html(),
        taskTemplate: $('#task-display-template').html(),

        events: {
            'click #todo-submit': 'upsertTodo',
            'click #todo-cancel': 'resetValues',
            'click #todo-tag-img': 'displayTagSelection',
            'click .add-task' : 'showTaskForm',
            'click #task-add-button': 'addTask',
            'click #task-cancel-button': 'cancelTask',
            'click .delete': 'deleteTask',
            'click .edit': 'editTask'
        },

        showTaskForm: function(e) {

            if (e) {
                e.preventDefault();
            }
            this.$el.find('.add-task').addClass('invisible').fadeOut();
            this.$el.find('#task-form').removeClass('invisible').fadeIn();
        },

        getModel: function() {
            return new Todo({
                'title': '',
                tasks: [],
                tags: []
            });
        },

        buildModel: function(entity) {

        },

        prepareVariables: function() {
            this.taskCollection = new Tasks();
            this.saveForm = $('#todo-form');
            this.tasksDiv =  $('#tasks');
            this.searchTag = $('#todo-tag');
            this.tagImage = $('#todo-tag-img');

            this.saveForm.find('[name=percent-completion]').on('change', this.showPercentTitle);
        },

        showPercentTitle: function(e) {
            $(e.target).parent('.form-group').find('.percent-completion-label').html('Percent Completion: '
                + $(e.target).val() + "%");
        },

        initializeUpdateForm: function() {

        },

        upsertTodo: function(e) {

            var self = this;
            e.preventDefault();

            var entityId = self.saveForm.find('.entityId').html();

            if (entityId) {
                self.model = new Todo({
                    id : entityId,
                    todoId : entityId
                });
            } else {
                self.model = new Todo();
            }

            self.model.set({
                title: self.saveForm.find('[name=title]').val(),
                tasks: self.taskCollection
            });

            self.model.on('invalid', function(model, error) {
                self.renderErrors(error);
            });

            var result = self.model.save({
                success: function(response) {},
                error: function(error) {}
            });

            if (result) {
                result.complete(function(response){
                    if (response.status != 201 && response.status != 200) {
                        var errors = self.buildErrorObject(response, self);
                        self.renderErrors(errors);
                    } else {
                        var tags = self.searchTag.val();
                        if (!entityId) {
                            self.postCreation(response.responseText, "TODO", self.model.get('title'), 1, tags);
                            self.model.set({
                                id: response.responseText,
                                'createdOn': Math.floor(Date.now()),
                                'modifiedAt': Math.floor(Date.now()),
                                'tags': tags
                            });
                        } else {
                            self.postCreation(entityId, "TODO", self.model.get('title'), 0, tags);
                            self.model.set({
                                'id': entityId,
                                'modifiedAt': Math.floor(Date.now()),
                                'tags': tags
                            });
                            self.collection.remove(self.collection.at(self.findIndex(entityId)));
                        }
                        self.collection.unshift(self.model);
                        var entityList = self.buildEntityList();
                        backboneGlobalObj.trigger('entity:displaylist', entityList);
                    }
                });
            }
        },

        fetchTodos: function(e) {

        },

        buildEntityList: function() {

            var entityList = [];
            return entityList;
        },

        getDeletableModel: function(id) {

        },

        addTask: function(e) {
            e.preventDefault();

            var taskId = this.$el.find('.taskId').html();

            if (!taskId){
                this.model = new Task({
                    name : this.$el.find('[name=name]').val(),
                    task : this.$el.find('[name=task]').val(),
                    priority: this.$el.find('[name=priority]:checked').val(),
                    percentCompletion: this.$el.find('[name=percent-completion]').val()
                });
            } else {
                this.model = this.taskCollection.get(taskId);
                this.model.set({
                    name : this.$el.find('[name=name]').val(),
                    task : this.$el.find('[name=task]').val(),
                    priority: this.$el.find('[name=priority]:checked').val(),
                    percentCompletion: this.$el.find('[name=percent-completion]').val()
                });
            }

            var errors = this.model.validate(this.model.attributes);
            if (errors) {
                this.renderErrors(errors);
                return;
            }

            this.resetTask();
            this.taskCollection.add(this.model);
            this.renderTasks();
        },

        cancelTask: function(e) {
            e.preventDefault();

            this.resetTask();
        },

        resetTask: function() {

            this.$el.find('#task-form').addClass('invisible').fadeOut();
            this.$el.find('.add-task').removeClass('invisible').fadeIn();
            this.$el.find('.taskId').empty();
            this.$el.find('[name=name]').val('').removeClass('error-field');
            this.$el.find('.name-error').html('');
            this.$el.find('[name=task]').val('').removeClass('error-field');
            this.$el.find('.task-error').html('');
            this.$el.find('[name=percent-completion]').val(0);
            this.$el.find('.percent-completion-label').html('Percent Completion: 0 %');
        },

        editTask: function(e) {

            var taskDiv = $(e.target).closest('.task-entity');
            var name = taskDiv.find('.name').html();
            var task = taskDiv.find('.task').html().trim();
            var priority = taskDiv.find('.priority').html();
            var taskId = taskDiv.find('.id').html();
            var percentCompletion = taskDiv.find('.percentCompletion').html().split(' ');

            this.$el.find('.taskId').html(taskId);
            this.$el.find('[name=name]').val(name);
            this.$el.find('[name=task]').val(task);
            this.$el.find('[name=percent-completion]').val(percentCompletion);
            this.$el.find('[name=priority]').removeAttr('checked');
            this.$el.find('#' + priority.toLowerCase()).click();

            this.showTaskForm();
        },

        deleteTask: function(e) {

            var taskId = $(e.target).closest('div.task-entity').find('.id').html();
            this.taskCollection.remove(this.taskCollection.get(taskId));
            this.renderTasks();
        },

        buildTaskList: function() {

            var entityList = [];

            for (var i = 0; i < this.taskCollection.length; i++) {

                var entity = {
                    'id': this.taskCollection.models[i].cid,
                    'priority': this.taskCollection.models[i].attributes.priority,
                    'percentCompletion': this.taskCollection.models[i].attributes.percentCompletion,
                    'name': this.taskCollection.models[i].attributes.name,
                    'task': this.taskCollection.models[i].attributes.task
                };
                entityList.push(entity);
            }
            return entityList;
        },

        renderTasks: function() {

            this.tasksDiv.empty();
            var taskEntities = this.buildTaskList();
            var template = _.template(this.taskTemplate);

            this.tasksDiv.html(
                template({
                    'tasks': taskEntities
                }
            ));
        }

    });

    window.todoView = new TodoView({
        collection: new Todos()
    });

})(jQuery, window, document);