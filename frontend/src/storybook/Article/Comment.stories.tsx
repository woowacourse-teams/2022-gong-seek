import { BrowserRouter } from 'react-router-dom';

import Comment, { CommentProps } from '@/components/comment/Comment/Comment';
import { Meta, Story } from '@storybook/react';

export default {
	title: 'Article/Comment',
	component: Comment,
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

const Template: Story<CommentProps> = (args) => <Comment {...args} />;

export const DefaultComment = Template.bind({});

DefaultComment.args = {
	id: 1,
	author: {
		name: '자스민',
		avatarUrl: 'http://openimage.interpark.com/goods_image_big/0/3/2/7/8317700327e_l.jpg',
	},
	content:
		'댓글 예시를 적는 곳입니다. 댓글 예시입니다. 2줄 이상일때에 어떻게 처리할지 다루기 위해서 입력하는 곳입니다.',
	createdAt: '2022-07-08T19:03:11',
	isAuthor: false,
};
