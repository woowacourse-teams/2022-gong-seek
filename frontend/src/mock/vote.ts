import { VoteItems } from '@/api/vote';
import { rest } from 'msw';

interface VoteItemsWithId extends VoteItems {
	voteId: string;
}

interface VoteData {
	articleId: string;
	options: VoteItemsWithId[];
}

const data = localStorage.getItem('mock-votes');

const mockVotes = data ? (JSON.parse(data) as VoteData[]) : [];

export const VoteHandler = [
	rest.post<{ options: string[] }>(
		'http://192.168.0.155:8080/api/articles/:articleId/votes',
		(req, res, ctx) => {
			const { articleId } = req.params;
			const { options } = req.body;

			if (typeof articleId !== 'string') {
				return;
			}

			localStorage.setItem(
				'mock-votes',
				JSON.stringify(
					mockVotes.concat({
						articleId,
						options: options.map((option, idx) => ({
							voteId: String(idx),
							option,
							count: 0,
						})),
					}),
				),
			);

			return res(
				ctx.status(201),
				ctx.json({
					articleId,
				}),
			);
		},
	),

	rest.get('http://192.168.0.155:8080/api/articles/:articleId/votes', (req, res, ctx) => {
		const { articleId } = req.params;

		// const vote = mockVotes.find((mockVote) => mockVote.articleId === articleId);
		// if (typeof vote === 'undefined') {
		// 	return;
		// }

		return res(
			// ctx.status(200),
			// ctx.json({
			// 	vote,
			// }),

			ctx.status(200),
			ctx.json([
				{ option: '1번 항목', count: 10 },
				{ option: '2번 항목', count: 1 },
				{ option: '3번 항목', count: 2 },
				{ option: '4번 항목', count: 0 },
				{ option: '5번 항목', count: 3 },
			]),
		);
	}),

	rest.post('http://192.168.0.155:8080/api/articles/:articleId/votes/:voteId', (req, res, ctx) => {
		const { voteId, articleId } = req.params;

		const vote = mockVotes.find((mockVote) => mockVote.articleId === articleId);
		if (typeof vote === 'undefined') {
			throw new Error('투표를 찾지 못하였습니다');
		}

		const selectedOption = vote.options.find((option) => option.voteId === voteId);

		if (typeof selectedOption === 'undefined') {
			throw new Error('투표를 찾지 못하였습니다');
		}

		selectedOption.count += 1;

		localStorage.setItem('mock-votes', JSON.stringify(mockVotes));

		return res(ctx.status(201));
	}),
];
