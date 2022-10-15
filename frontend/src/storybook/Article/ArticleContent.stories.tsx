import ArticleContent, {
	ArticleContentProps,
} from '@/components/article/ArticleContent/ArticleContent';
import { Meta, Story } from '@storybook/react';

export default {
	title: 'Article/ArticleContent',
	component: ArticleContent,
	decorators: [
		(Story) => (
			<div style={{ width: '320px' }}>
				<Story />
			</div>
		),
	],
} as Meta;

const Template: Story<ArticleContentProps> = (args) => <ArticleContent {...args} />;

export const DefaultComment = Template.bind({});

DefaultComment.args = {
	category: '질문',
	article: {
		id: 1,
		title:
			'글 상세페이지에서의 글 제목이 들어가는 곳, 글 제목이 2줄 이상이 넘어갔을때 어떻게 처리할 것인지 처리하기 위한 예시 문장입니다',
		content:
			'글 상세페이지에서 글의 내용이 들어가는 곳, 이곳에서는 TextEditorViews가 보여지는 곳입니다',
		createdAt: '2022-08-11T13:34:11',
		views: 10,
		likeCount: 10,
		isLike: true,
		isAuthor: true,
		hasVote: false,
		tag: ['1', '2', '3'],
		author: {
			name: '자스민',
			avatarUrl: 'http://openimage.interpark.com/goods_image_big/0/3/2/7/8317700327e_l.jpg',
		},
		category: 'discussion',
		commentCount: 2,
	},
	author: {
		name: '자스민',
		avatarUrl: 'http://openimage.interpark.com/goods_image_big/0/3/2/7/8317700327e_l.jpg',
	},
};
