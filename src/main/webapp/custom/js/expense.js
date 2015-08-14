(function($, window, document){

    "use strict"

    var EXPENSE_URL_ROOT = '/iPersonal/dashboard/expenses';
    var EXPENSE_SEARCH_URL_ROOT = '/iPersonal/dashboard/expenses/search';

    var Expense = Base.extend({
    	urlRoot: EXPENSE_URL_ROOT,
        
        initialize: function() {
            this.mandatory = {
                'title': true, 'amount': true, 'description': true, 'date': true
            },
            this.maxLength = {
                'title': 50, 'description': 1000
            },
            this.type = {
                'amount': 'double'
            },
            this.formAttributes = ['title', 'description', 'amount', 'date', 'categories']
        }

    });

    var ExpenseSearch = Backbone.Model.extend({
        urlRoot: EXPENSE_SEARCH_URL_ROOT
    });

    var Expenses = Backbone.Collection.extend({
        model: Expense
    })

    var ExpenseView = BaseView.extend({

        el: $('#expense-wrapper'),
        entityType: 'EXPENSE',
        createTemplate: $('#expense-create-template').html(),
        displayTemplate: $('#expense-display-template').html(),

        events : {
            'click #exp-submit': 'createExpense',
            'click #exp-cancel': 'resetValues',
            'click #exp-tag-img': 'displayTagSelection',
        },

        prepareVariables: function() {

            this.saveForm = $('#expense-form');
            this.tagImage = $('#exp-tag-img');
            this.searchTag = $('#expense-tag');
            this.categoryDropDown = $('#expense-form').find('[name=categories]');
            this.populateCategories();
        },

        resetValues: function(e) {

            if (e) {
                e.preventDefault();
            }
            BaseView.prototype.resetValues.apply(this, arguments);
            this.categoryDropDown.val('').trigger('chosen:updated');
        },

        populateCategories: function(expenseCategories) {

            var categories = expenseCategoryModel.getExpenseCategories();
            if (categories) {
                this.categoryDropDown.empty();
                for (var i = 0; i < categories.length; i++) {
                    this.categoryDropDown.append($('<option></option>').attr('value', categories[i]).text(categories[i]));
                }
                this.categoryDropDown.trigger('chosen:updated');
            }
        },

        getDeletableModel: function(id) {

            return new Expense({
                id: id
            });
        },

        findIndex: function(id) {
            for (var i = 0; i < this.collection.models.length; i++) {
                if (this.collection.models[i].attributes.id == id) {
                    break;
                }
            }
            return i;
        },

        createExpense: function(e) {

            var self = this;
            e.preventDefault();

            self.model = new Expense();
            self.model.set({
                title: self.saveForm.find('[name=title]').val(),
                description: self.saveForm.find('[name=description]').val(),
                amount: self.saveForm.find('[name=amount]').val(),
                date: (new Date(self.saveForm.find('[name=date]').val()).getTime() / 1000).toFixed(0),
                categories: self.saveForm.find('[name=categories]').val()
            });

            self.model.on('invalid', function(model , error) {
                self.renderErrors(error);
            });

            var result = self.model.save({
                success: function(response) {},
                error: function(error) {}
            });

            if (result) {
                result.complete(function(response){
                    if (response.status != 201) {
                        var errors = self.buildErrorObject(response, self);
                        self.renderErrors(errors);
                    } else {
                        var id = response.responseText;
                        var tags = self.searchTag.val();
                        self.postCreation(id, "EXPENSE", self.model.get('title'), 1, tags, self.model.get('categories'))
                        self.model.set({
                            id : id,
                            'createdOn': Math.floor(Date.now()),
                            'modifiedAt': Math.floor(Date.now()),
                            'tags': tags
                        });
                        self.collection.unshift(self.model);
                        var entityList = self.buildEntityList();
                        backboneGlobalObj.trigger('entity:displaylist', entityList);
                    }
                });
            }
        },

        fetchExpenses: function(e) {

            var self = this;

            if (e) {
                e.preventDefault();
            }

            if (this.collection.length  == parseInt(entityCountModel.attributes.expenses)) {
                var entityList = this.buildEntityList();
                backboneGlobalObj.trigger('entity:displaylist', entityList);
                return;
            }
             
            this.model = new ExpenseSearch();
            this.model.set({ url: '/iPersonal/dashboard/expenses/search'})
            this.model.save({data: {offset: this.collection.length, limit : 20} }).complete(function(response){
                if (response.status == 200) {
                    var expenses = JSON.parse(response.responseText)['expense'];
                    if (expenses instanceof Array) {
                        for (var index in expenses) {
                            var expense = new Expense(expenses[index]);
                            expense.set({ id : expenses[index]['expenseId']});
                            self.collection.push(expense);
                        }
                    } else if (expenses) {
                        var expense = new Expense(expenses);
                        expense.set({ id : expenses['expenseId']});
                        self.collection.push(expense);
                    }
                    var entityList = self.buildEntityList();
                    backboneGlobalObj.trigger('entity:displaylist', entityList);
                }
            });
        },

        buildEntityList: function() {

            var entityList = [];

            for (var i = 0; i < this.collection.length; i++) {
                var description = this.collection.models[i].attributes.description;
                var entity = {
                    'entityId' : this.collection.models[i].attributes.id,
                    'entityTitle' : this.collection.models[i].attributes.title,
                    'amount': this.collection.models[i].attributes.amount,
                    'entitySummary': description ? description.substring(0,100) : description,
                    'entityType': 'expense',
                    'modifiedAt': this.collection.models[i].attributes.modifiedAt,
                };
                entityList.push(entity);
            }
            return entityList;
        }
    });

    window.expenseView = new ExpenseView({ collection: new Expenses() });

})(jQuery, window, document);