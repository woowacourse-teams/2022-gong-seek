// import { rest } from 'msw';

// import { TVote } from '@/api/vote';
// import { HOME_URL } from '@/constants/url';

// const data = localStorage.getItem('mock-votes');

// const mockVotes = data ? (JSON.parse(data) as TVote[]) : [];

export const VoteHandler = [
	rest.post<{ items: string[] }>(`${HOME_URL}/api/articles/:articleId/votes`, (req, res, ctx) => {
		const data = localStorage.getItem('mock-votes');
		const mockVotes = data ? (JSON.parse(data) as TVote[]) : [];

		const { articleId } = req.params;
		const { items } = req.body;

// export const VoteHandler = [
// 	rest.post<{ items: string[] }>(`${HOME_URL}/api/articles/:articleId/votes`, (req, res, ctx) => {
// 		const { articleId } = req.params;
// 		const { items } = req.body;

// 		if (typeof articleId !== 'string') {
// 			return;
// 		}

// 		localStorage.setItem(
// 			'mock-votes',
// 			JSON.stringify(
// 				mockVotes.concat({
// 					articleId,
// 					options: items.map((item, idx) => ({
// 						voteId: String(idx),
// 						option: item,
// 						count: 0,
// 					})),
// 				}),
// 			),
// 		);

	rest.get(`${HOME_URL}/api/articles/:articleId/votes`, (req, res, ctx) => {
		const { articleId } = req.params;
		const data = localStorage.getItem('mock-votes');
		const mockVotes = data ? (JSON.parse(data) as TVote[]) : [];

		const vote = mockVotes.find((mockVote) => mockVote.articleId === articleId);
		if (typeof vote === 'undefined') {
			throw new Error('투표를 찾지 못하였습니다');
		}

// 		if (typeof vote === 'undefined') {
// 			throw new Error('투표를 찾지 못하였습니다');
// 		}

	rest.post<{ votedItemId: string }>(
		`${HOME_URL}/api/articles/:articleId/votes/do`,
		(req, res, ctx) => {
			const data = localStorage.getItem('mock-votes');
			const mockVotes = data ? (JSON.parse(data) as TVote[]) : [];

			const { articleId } = req.params;
			const { votedItemId } = req.body;

// 	rest.post(`${HOME_URL}/api/articles/:articleId/votes/:voteId`, (req, res, ctx) => {
// 		const { voteId, articleId } = req.params;

// 		const vote = mockVotes.find((mockVote) => mockVote.articleId === articleId);
// 		if (typeof vote === 'undefined') {
// 			throw new Error('투표를 찾지 못하였습니다');
// 		}

// 		const selectedOption = vote.options.find((option) => option.voteId === voteId);

// 		if (typeof selectedOption === 'undefined') {
// 			throw new Error('투표를 찾지 못하였습니다');
// 		}

// 		selectedOption.count += 1;

// 		localStorage.setItem('mock-votes', JSON.stringify(mockVotes));

// 		return res(ctx.status(201));
// 	}),
// ];
