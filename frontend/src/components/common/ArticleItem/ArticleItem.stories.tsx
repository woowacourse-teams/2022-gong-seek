import ArticleItem, { ArticleItemProps } from '@/components/common/ArticleItem/ArticleItem';
import { Meta, Story } from '@storybook/react';

export default {
	title: 'common/ArticleItem',
	component: ArticleItem,
	decorators: [
		(Story) => (
			<div style={{ width: '320px' }}>
				<Story />
			</div>
		),
	],
} as Meta;

const Template: Story<ArticleItemProps> = (args) => <ArticleItem {...args} />;

export const DiscussionArticleItem = Template.bind({});

DiscussionArticleItem.args = {
	article: {
		id: 1,
		title: '예제',
		author: {
			name: '지은이',
			avatarUrl: 'http://openimage.interpark.com/goods_image_big/0/3/2/7/8317700327e_l.jpg',
		},
		content: '예시 내용입니다',
		category: 'discussion',
		commentCount: 10,
		createdAt: '2022-02-07T10:02',
		hashTag: ['리액트', '프론트엔드'],
		views: 10,
	},
};

export const ErrorArticleItem = Template.bind({});

ErrorArticleItem.args = {
	article: {
		id: 1,
		title: '예제',
		author: {
			name: '지은이',
			avatarUrl: 'http://openimage.interpark.com/goods_image_big/0/3/2/7/8317700327e_l.jpg',
		},
		content: '예시 내용입니다',
		category: 'discussion',
		commentCount: 10,
		createdAt: '2022-02-07T10:02',
		hashTag: ['리액트', '프론트엔드'],
		views: 10,
	},
};
