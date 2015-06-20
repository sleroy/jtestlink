
$(document).ready(function() {
	
	/*var width = 960,
	    height = 700,
	    radius = Math.min(width, height) / 2,
	    color = d3.scale.category20c();
	
	var svg = d3.select(".projects-sunburst").append("svg")
	    .attr("width", width)
	    .attr("height", height)
	  .append("g")
	    .attr("transform", "translate(" + width / 2 + "," + height * .52 + ")")
	  .append("g")
	    .classed("inner", true);
	
	var partition = d3.layout.partition()
	    .sort(null)
	    .size([2 * Math.PI, radius * radius])
	    .value(function(d) { return 1; });
	
	var arc = d3.svg.arc()
	            .startAngle(function(d) { return d.x + Math.PI / 2; })
	            .endAngle(function(d) { return d.x + d.dx + Math.PI / 2; })
	            .innerRadius(function(d) { return Math.sqrt(d.y); })
	            .outerRadius(function(d) { return Math.sqrt(d.y + d.dy); });
	
	d3.json("/resources/sunburst_sample.json", function(error, root) {
		console.log(root);
	  var path = svg.datum(root).selectAll("path")
	      .data(partition.nodes)
	    .enter().append("path")
	      .attr("display", function(d) { return d.depth ? null : "none"; }) // hide inner ring
	      .attr("d", arc)
	      .style("fill", function(d) { return color((d.children ? d : d.parent).name); })
	      .style("fill-rule", "evenodd");
	
	  var textBox = d3.select("svg").selectAll("text")
	      .data(["Click on a path"])
	    .enter().append("text")
	      .attr("transform", "translate(" + width + ",0)")
	      .attr("text-anchor", "end")
	      .attr("dy", 16)
	      .text(String);
	
	  var innerG = d3.selectAll("g.inner");
	
	  d3.selectAll("path").on("click", function (d, i) {
	    var newAngle = - (d.x + d.dx / 2);
	
	    innerG
	      .transition()
	        .duration(1500)
	        .attr("transform", "rotate(" + (180 / Math.PI * newAngle) + ")");
	
	    path
	      .classed("selected", function (x) { return d.name == x.name; });
	
	    textBox.data(["Clicked: " + d.name])
	        .text(String);
	  });
	});
	
	console.log(self.frameElement);
	d3.select(self.frameElement).style("height", height + "px");
	*/
	
	var elt = '.projects-sunburst';
	
	function drawSunburst() {
		
		var width = $(elt).parent().width();
		console.log( width );
		
		new SunburstBuilder()
				.setRoot(elt)
				.setHeight(width)
				.setWidth(width)
				.setURL('/resources/sunburst_sample.json')
				.onClick(function(data) {
					console.log(data);
				})
				.build();
		
	}
	
	drawSunburst();
	$(window).resize(function() {
		$(elt).html('');
		drawSunburst();
	});
	
});
