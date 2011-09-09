scriptLoader.loadCommon("jquery/jquery-latest.js");

function CommunityVotes(docId, fetchUrl, voteUrl) {
	this.docId = docId;
	this.voteUrl = voteUrl;
	this.fetchUrl = fetchUrl;
}

CommunityVotes.prototype.fetch = function() {
	$.ajax({
		url : this.fetchUrl,
		dataType : "json",
		data : {
			doc_id : this.docId
		},
		success : function(data) {
			$("#positiveCount").html(data.positive);
			$("#negativeCount").html(data.negative);
			if(!data.voted) {
				$("#positiveVote").click(function(){
		             communityVotes.vote('positive');
		        });
		        $("#negativeVote").click(function(){
		             communityVotes.vote("negative");
		        });
			}
		}
	});
};

CommunityVotes.prototype.vote = function(vote) {
	$.ajax({
		url : this.voteUrl,
		dataType : "jsonp",
		data : {
			doc_id : this.docId,
			vote : vote
		},
		success : function(data) {
			$("#positiveCount").html(data.positive);
			$("#negativeCount").html(data.negative);
			$("#positiveVote").unbind("click");
	        $("#negativeVote").unbind("click");
		}
	});
};