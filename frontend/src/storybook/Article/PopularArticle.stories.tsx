import { rest } from 'msw';
import { BrowserRouter } from 'react-router-dom';

import PopularArticleCarousel from '@/components/article/PopularArticleCarousel/PopularArticleCarousel';
import { HOME_URL } from '@/constants/apiUrl';
import { Meta, Story } from '@storybook/react';

export default {
	title: 'Article/PopularArticle',
	component: PopularArticleCarousel,
	decorators: [
		(Story) => (
			<BrowserRouter>
				<div style={{ width: '320px' }}>
					<Story />
				</div>
			</BrowserRouter>
		),
	],
} as Meta;

const Template: Story = (args) => <PopularArticleCarousel {...args} />;

export const DefaultPopularArticle = Template.bind({});
DefaultPopularArticle.args = {};

DefaultPopularArticle.parameters = {
	msw: {
		handlers: [
			rest.get(`${HOME_URL}/api/articles`, (req, res, ctx) =>
				res(
					ctx.status(200),
					ctx.json({
						articles: [
							{
								id: 0,
								title:
									'글 상세페이지에서의 글 제목이 들어가는 곳, 글 제목이 2줄 이상이 넘어갔을때 어떻게 처리할 것인지 처리하기 위한 예시 문장입니다',
								content:
									'글 상세페이지에서 글의 내용이 들어가는 곳, 이곳에서는 TextEditorViews가 보여지는 곳입니다',
								createdAt: '2022-08-11T13:34:11',
								views: 10,
								likeCount: 10,
								isLike: true,
								tag: ['1', '2', '3'],
								author: {
									name: '자스민',
									avatarUrl:
										'http://openimage.interpark.com/goods_image_big/0/3/2/7/8317700327e_l.jpg',
								},
								category: 'discussion',
								commentCount: 2,
								isAuthor: true,
							},
							{
								id: 1,
								title:
									'글 상세페이지에서의 글 제목이 들어가는 곳, 글 제목이 2줄 이상이 넘어갔을때 어떻게 처리할 것인지 처리하기 위한 예시 문장입니다',
								content:
									'글 상세페이지에서 글의 내용이 들어가는 곳, 이곳에서는 TextEditorViews가 보여지는 곳입니다',
								createdAt: '2022-08-11T13:34:11',
								views: 10,
								likeCount: 10,
								isLike: true,
								tag: ['1', '2', '3'],
								author: {
									name: '자스민',
									avatarUrl:
										'http://openimage.interpark.com/goods_image_big/0/3/2/7/8317700327e_l.jpg',
								},
								category: 'discussion',
								commentCount: 2,
								isAuthor: true,
							},
							{
								id: 2,
								title:
									'글 상세페이지에서의 글 제목이 들어가는 곳, 글 제목이 2줄 이상이 넘어갔을때 어떻게 처리할 것인지 처리하기 위한 예시 문장입니다',
								content:
									'글 상세페이지에서 글의 내용이 들어가는 곳, 이곳에서는 TextEditorViews가 보여지는 곳입니다',
								createdAt: '2022-08-11T13:34:11',
								views: 10,
								likeCount: 10,
								isLike: true,
								tag: ['1', '2', '3'],
								author: {
									name: '자스민',
									avatarUrl:
										'http://openimage.interpark.com/goods_image_big/0/3/2/7/8317700327e_l.jpg',
								},
								category: 'discussion',
								commentCount: 2,
								isAuthor: true,
							},
							{
								id: 3,
								title:
									'글 상세페이지에서의 글 제목이 들어가는 곳, 글 제목이 2줄 이상이 넘어갔을때 어떻게 처리할 것인지 처리하기 위한 예시 문장입니다',
								content:
									'글 상세페이지에서 글의 내용이 들어가는 곳, 이곳에서는 TextEditorViews가 보여지는 곳입니다',
								createdAt: '2022-08-11T13:34:11',
								views: 10,
								likeCount: 10,
								isLike: true,
								tag: ['1', '2', '3'],
								author: {
									name: '자스민',
									avatarUrl:
										'http://openimage.interpark.com/goods_image_big/0/3/2/7/8317700327e_l.jpg',
								},
								category: 'discussion',
								commentCount: 2,
								isAuthor: true,
							},
							{
								id: 4,
								title:
									'글 상세페이지에서의 글 제목이 들어가는 곳, 글 제목이 2줄 이상이 넘어갔을때 어떻게 처리할 것인지 처리하기 위한 예시 문장입니다',
								content:
									'글 상세페이지에서 글의 내용이 들어가는 곳, 이곳에서는 TextEditorViews가 보여지는 곳입니다',
								createdAt: '2022-08-11T13:34:11',
								views: 10,
								likeCount: 10,
								isLike: true,
								tag: ['1', '2', '3'],
								author: {
									name: '자스민',
									avatarUrl:
										'http://openimage.interpark.com/goods_image_big/0/3/2/7/8317700327e_l.jpg',
								},
								category: 'discussion',
								commentCount: 2,
								isAuthor: true,
							},
							{
								id: 5,
								title:
									'글 상세페이지에서의 글 제목이 들어가는 곳, 글 제목이 2줄 이상이 넘어갔을때 어떻게 처리할 것인지 처리하기 위한 예시 문장입니다',
								content:
									'글 상세페이지에서 글의 내용이 들어가는 곳, 이곳에서는 TextEditorViews가 보여지는 곳입니다',
								createdAt: '2022-08-11T13:34:11',
								views: 10,
								likeCount: 10,
								isLike: true,
								tag: ['1', '2', '3'],
								author: {
									name: '자스민',
									avatarUrl:
										'http://openimage.interpark.com/goods_image_big/0/3/2/7/8317700327e_l.jpg',
								},
								category: 'discussion',
								commentCount: 2,
								isAuthor: true,
							},
							{
								id: 6,
								title:
									'글 상세페이지에서의 글 제목이 들어가는 곳, 글 제목이 2줄 이상이 넘어갔을때 어떻게 처리할 것인지 처리하기 위한 예시 문장입니다',
								content:
									'글 상세페이지에서 글의 내용이 들어가는 곳, 이곳에서는 TextEditorViews가 보여지는 곳입니다',
								createdAt: '2022-08-11T13:34:11',
								views: 10,
								likeCount: 10,
								isLike: true,
								tag: ['1', '2', '3'],
								author: {
									name: '자스민',
									avatarUrl:
										'http://openimage.interpark.com/goods_image_big/0/3/2/7/8317700327e_l.jpg',
								},
								category: 'discussion',
								commentCount: 2,
								isAuthor: true,
							},
							{
								id: 7,
								title:
									'글 상세페이지에서의 글 제목이 들어가는 곳, 글 제목이 2줄 이상이 넘어갔을때 어떻게 처리할 것인지 처리하기 위한 예시 문장입니다',
								content:
									'글 상세페이지에서 글의 내용이 들어가는 곳, 이곳에서는 TextEditorViews가 보여지는 곳입니다',
								createdAt: '2022-08-11T13:34:11',
								views: 10,
								likeCount: 10,
								isLike: true,
								tag: ['1', '2', '3'],
								author: {
									name: '자스민',
									avatarUrl:
										'http://openimage.interpark.com/goods_image_big/0/3/2/7/8317700327e_l.jpg',
								},
								category: 'discussion',
								commentCount: 2,
								isAuthor: true,
							},
							{
								id: 8,
								title:
									'글 상세페이지에서의 글 제목이 들어가는 곳, 글 제목이 2줄 이상이 넘어갔을때 어떻게 처리할 것인지 처리하기 위한 예시 문장입니다',
								content:
									'글 상세페이지에서 글의 내용이 들어가는 곳, 이곳에서는 TextEditorViews가 보여지는 곳입니다',
								createdAt: '2022-08-11T13:34:11',
								views: 10,
								likeCount: 10,
								isLike: true,
								tag: ['1', '2', '3'],
								author: {
									name: '자스민',
									avatarUrl:
										'http://openimage.interpark.com/goods_image_big/0/3/2/7/8317700327e_l.jpg',
								},
								category: 'discussion',
								commentCount: 2,
								isAuthor: true,
							},
							{
								id: 9,
								title:
									'글 상세페이지에서의 글 제목이 들어가는 곳, 글 제목이 2줄 이상이 넘어갔을때 어떻게 처리할 것인지 처리하기 위한 예시 문장입니다',
								content:
									'글 상세페이지에서 글의 내용이 들어가는 곳, 이곳에서는 TextEditorViews가 보여지는 곳입니다',
								createdAt: '2022-08-11T13:34:11',
								views: 10,
								likeCount: 10,
								isLike: true,
								tag: ['1', '2', '3'],
								author: {
									name: '자스민',
									avatarUrl:
										'http://openimage.interpark.com/goods_image_big/0/3/2/7/8317700327e_l.jpg',
								},
								category: 'discussion',
								commentCount: 2,
								isAuthor: true,
							},
						],
						hasNext: false,
					}),
				),
			),
		],
	},
};
