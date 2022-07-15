export const convertIdxToColorKey = (idx: number) => {
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
