<%-- garils template renderer copies all model map items into the page binding
so as long as you know what was in the model as map entries - you can just 'get' that
value directly

so _wrapper is invoked by f:field, after its calculated the rendered widget (entry field formatted)
and stores in the model as model[widget] = 'rendered widget'.

 so here we can just reclaim that rendered widget from the templates binding and display directly

 use the _wrapper.gsp to add any additional html to wrap the rendered widget - in this case none

 --%>
${this.widget}
