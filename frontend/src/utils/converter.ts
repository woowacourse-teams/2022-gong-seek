export const convertIdxToVoteColorKey = (idx: number) => {
	switch (idx) {
		case 0:
			return 'VOTE_001';
		case 1:
			return 'VOTE_002';
		case 2:
			return 'VOTE_003';
		case 3:
			return 'VOTE_004';
		case 4:
			return 'VOTE_005';
		default:
			return 'VOTE_001';
	}
};

export const convertIdxToArticleColorKey = (idx: number) => {
	switch (idx) {
		case 0:
			return 'ARTICLE_001';
		case 1:
			return 'ARTICLE_002';
		case 2:
			return 'ARTICLE_003';
		case 3:
			return 'ARTICLE_004';
		case 4:
			return 'ARTICLE_005';
		case 5:
			return 'ARTICLE_006';
		case 6:
			return 'ARTICLE_007';
		case 7:
			return 'ARTICLE_008';
		case 8:
			return 'ARTICLE_009';
		case 9:
			return 'ARTICLE_010';
		default:
			return 'ARTICLE_001';
	}
};
