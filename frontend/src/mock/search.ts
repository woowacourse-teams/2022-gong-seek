import { rest } from 'msw';

import { HOME_URL } from '@/constants/apiUrl';
import { SearchResultType } from '@/types/searchResponse';

export const SearchHandler = [
	rest.get<SearchResultType>(`${HOME_URL}/api/articles/search/author`, (req, res, ctx) => {
		const cursorId = req.url.searchParams.get('cursorId');
		const pageSize = req.url.searchParams.get('size');
		const author = req.url.searchParams.get('author');

		if (typeof author === 'undefined') {
			return;
		}

		return res(
			ctx.status(200),
			ctx.json({
				articles: [
					{
						id: 1,
						title: `ssss`,
						author: {
							name: `${author}`,
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
						title: `elelel`,
						author: {
							name: `${author}`,
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
						title: `elelel`,
						author: {
							name: `${author}`,
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
	rest.get<SearchResultType>(`${HOME_URL}/api/articles/search/text`, (req, res, ctx) => {
		const cursorId = req.url.searchParams.get('cursorId');
		const pageSize = req.url.searchParams.get('size');
		const searchText = req.url.searchParams.get('text');

		if (typeof searchText === 'undefined') {
			return;
		}
		return res(
			ctx.status(200),
			ctx.json({
				articles: [
					{
						id: 1,
						title: `${searchText}`,
						author: {
							name: `sally`,
							avatarUrl: '작성자1 이미지 url',
						},
						content: `${searchText}`,
						category: 'question',
						commentCount: 3,
						views: 2,
						createdAt: '2022-08-01T20:27:08.6877197',
					},
					{
						id: 2,
						title: `${searchText}`,
						author: {
							name: `sally`,
							avatarUrl: '작성자1 이미지 url',
						},
						content: `${searchText}`,
						category: 'question',
						commentCount: 3,
						views: 2,
						createdAt: '2022-08-01T20:27:08.6877197',
					},
					{
						id: 3,
						title: `${searchText}`,
						author: {
							name: `author`,
							avatarUrl: '작성자1 이미지 url',
						},
						content: `${searchText}`,
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
