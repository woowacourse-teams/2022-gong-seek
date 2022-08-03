import { rest } from 'msw';

import { HOME_URL } from '@/constants/url';
import { SearchResultType } from '@/types/searchResponse';

export const SearchHandler = [
	rest.get<SearchResultType>(`${HOME_URL}/api/articles/search`, (req, res, ctx) => {
		const cursorId = req.url.searchParams.get('cursorId');
		const pageSize = req.url.searchParams.get('pageSize');
		const searchText = req.url.searchParams.get('searchText');

		if (typeof searchText === 'undefined') {
			return;
		}

		return res(
			ctx.status(200),
			ctx.json({
				articles: [
					{
						id: 1,
						title: `${searchText}elelel`,
						author: {
							name: '작성자1',
							avatarUrl: '작성자1 이미지 url',
						},
						content: '내용',
						category: 'question',
						commentCount: 3,
						views: 2,
						createdAt: '2022-08-01T20:27:08.6877197',
					},
					{
						id: 2,
						title: `${searchText}elelel`,
						author: {
							name: '작성자1',
							avatarUrl: '작성자1 이미지 url',
						},
						content: '내용',
						category: 'question',
						commentCount: 3,
						views: 2,
						createdAt: '2022-08-01T20:27:08.6877197',
					},
					{
						id: 3,
						title: `${searchText}elelel`,
						author: {
							name: '작성자1',
							avatarUrl: '작성자1 이미지 url',
						},
						content: '내용',
						category: 'question',
						commentCount: 3,
						views: 2,
						createdAt: '2022-08-01T20:27:08.6877197',
					},
				],
				hasNext: false,
			}),
		);
	}),
];
