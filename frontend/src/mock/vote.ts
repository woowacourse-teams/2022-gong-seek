import { rest } from 'msw';

import { VoteItems } from '@/api/vote';
import { HOME_URL } from '@/constants/url';

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
	rest.post<{ options: string[] }>(`${HOME_URL}/api/articles/:articleId/votes`, (req, res, ctx) => {
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
	}),

	rest.get(`${HOME_URL}/api/articles/:articleId/votes`, (req, res, ctx) => {
		const { articleId } = req.params;

		const vote = mockVotes.find((mockVote) => mockVote.articleId === articleId);

		if (typeof vote === 'undefined') {
			throw new Error('투표를 찾지 못하였습니다');
		}

		return res(ctx.status(200), ctx.json([...vote.options]));
	}),

	rest.post(`${HOME_URL}/api/articles/:articleId/votes/:voteId`, (req, res, ctx) => {
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
