import Ember from 'ember';

export default Ember.ArrayController.extend({
  rootElement: null,
  // model: [],

  actions: {
    createGroup: function () {
      // this.propertyWillChange('content');
    	var record = this.store.createRecord('group', {
    		label: 'New group',
    	});
    	record.save();

      this.get('model').pushObject(record);
      // this.propertyDidChange('content');

      
      // this.store.filter('group', function (record) {
      //   return record.get('root');
      // }).then(function (groups) {
      //   groups.objectAt(0).get('children').pushObject(record);
      // });

      // rootElement.save();

    	// this.store.find('group', {root: true}).then(function (group) {
     //    console.log(group.objectAt(0));
    	// 	group.objectAt(0).get('children').pushObject(record);
     //    group.save();
    	// });
    }
  },

  // rootElementObserver: Ember.observer(function() {
  //   return false;
  //   var root = this.get('rootElement');

  //   if (root.get('length') > 0) {
  //       var content = [];

  //       for (var index = 0; index < root.get('length'); index++) {
  //       //     //console.log('pushing object: ' + root.objectAt(index).get('name'));
  //         content.pushObject(root.objectAt(index));
  //       }

  //       this.set('content', content);
  //   }
  // }, 'rootElement.length')
});
