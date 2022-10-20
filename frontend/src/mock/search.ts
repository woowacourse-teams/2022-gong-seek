import { rest } from 'msw';

import { HOME_URL } from '@/constants/apiUrl';
import { CommonArticleType } from '@/types/articleResponse';
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

	rest.get<{ articles: CommonArticleType[] }>(
		`${HOME_URL}/api/articles/search/tags`,
		(req, res, ctx) =>
			res(
				ctx.status(200),
				ctx.json({
					articles: [
						{
							id: 1,
							title: '게시글 상세 조회 예시 타이틀',
							author: {
								name: '샐리',
								avatarUrl:
									'http://openimage.interpark.com/goods_image_big/0/3/2/7/8317700327e_l.jpg',
							},
							content: '게시글 상세 조회 예시 markdown이 보여질 곳 ',
							category: 'discussion',
							views: 10,
							isAuthor: true,
							createdAt: '2022-07-19T13:32',
							updatedAt: '2022-07-29T16:55',
							tag: ['리액트', '프론트엔드'],
							likeCount: 10,
							commentCount: 10,
							isLike: true,
						},
						{
							id: 2,
							title: '게시글 상세 조회 예시 타이틀',
							author: {
								name: '샐리',
								avatarUrl:
									'http://openimage.interpark.com/goods_image_big/0/3/2/7/8317700327e_l.jpg',
							},
							content: '게시글 상세 조회 예시 markdown이 보여질 곳 ',
							category: 'discussion',
							views: 10,
							isAuthor: true,
							createdAt: '2022-07-19T13:32',
							updatedAt: '2022-07-29T16:55',
							tag: ['리액트', '프론트엔드'],
							likeCount: 10,
							commentCount: 10,
							isLike: true,
						},
						{
							id: 3,
							title: '게시글 상세 조회 예시 타이틀',
							author: {
								name: '샐리',
								avatarUrl:
									'http://openimage.interpark.com/goods_image_big/0/3/2/7/8317700327e_l.jpg',
							},
							content: '게시글 상세 조회 예시 markdown이 보여질 곳 ',
							category: 'discussion',
							views: 10,
							isAuthor: true,
							createdAt: '2022-07-19T13:32',
							updatedAt: '2022-07-29T16:55',
							tag: ['리액트', '프론트엔드'],
							likeCount: 10,
							commentCount: 10,
							isLike: true,
						},
					],
				}),
			),
	),
];
