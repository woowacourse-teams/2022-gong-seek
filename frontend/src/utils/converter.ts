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

const convertIdxToTimeKey = (idx: number) => {
	switch (idx) {
		case 0:
			return '년';
		case 1:
			return '달';
		case 2:
			return '일';
		case 3:
			return '시간';
		case 4:
			return '분';
		default:
			return '방금';
	}
};

const timePicker = (timeArray: number[]) => {
	for (let i = 0; i < timeArray.length - 1; i++) {
		if (timeArray[i] > 0) {
			return { value: timeArray[i], key: convertIdxToTimeKey(i) };
		}
	}

	return { key: convertIdxToTimeKey(timeArray.length - 1) };
};

export const dateTimeConverter = (dateTime: string) => {
	const [date, time] = dateTime.split('T');

	const [year, month, day] = date.split('-').map((e) => Number(e));
	const [hour, minute, second] = time.split(':').map((e) => Number(e));

	const createdAt = new Date(year, month - 1, day, hour, minute, second || 0);

	const timeGap = (Date.now() - createdAt.getTime()) / 1000;
	const yearGap = Math.floor(timeGap / (3600 * 24 * 30 * 12));
	const monthGap = Math.floor((timeGap / (3600 * 24 * 30)) % 12);
	const dayGap = Math.floor((timeGap / (3600 * 24)) % 30);
	const hourGap = Math.floor((timeGap / 3600) % 24);
	const minuteGap = Math.floor((timeGap / 60) % 60);
	const secondGap = Math.floor(timeGap % 60);

	const selectedTime = timePicker([yearGap, monthGap, dayGap, hourGap, minuteGap, secondGap]);

	return `${selectedTime.value ? selectedTime.value : ''}${selectedTime.key}전`;
};
