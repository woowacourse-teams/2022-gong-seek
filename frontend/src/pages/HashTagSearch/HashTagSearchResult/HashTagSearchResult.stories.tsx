import HashTagSearchResult, {
	HashTagSearchResultProps,
} from '@/pages/HashTagSearch/HashTagSearchResult/HashTagSearchResult';
import { Meta, Story } from '@storybook/react';

export default {
	title: 'pages/HashTagSearch/HashTagSearchResult',
	component: HashTagSearchResult,
	decorators: [
		(Story) => (
			<div style={{ width: '320px' }}>
				<Story />
			</div>
		),
	],
} as Meta;

const Template: Story<HashTagSearchResultProps> = (args) => <HashTagSearchResult {...args} />;

export const DefaultVote = Template.bind({});
DefaultVote.args = {
	articles: [
		{
			id: 1,
			title: '검색결과1',
			author: {
				name: '샐리',
				avatarUrl: '',
			},
			content: '검색결과 예시입니다',
			category: 'question',
			commentCount: 10,
			createdAt: '2022-08-11T16:09',
			views: 11,
			isLike: true,
			likeCount: 10,
		},
		{
			id: 2,
			title: '검색결과1',
			author: {
				name: '샐리',
				avatarUrl: '',
			},
			content: '검색결과 예시입니다',
			category: 'question',
			commentCount: 10,
			createdAt: '2022-08-11T16:09',
			views: 11,
			isLike: true,
			likeCount: 10,
		},
		{
			id: 3,
			title: '검색결과1',
			author: {
				name: '샐리',
				avatarUrl: '',
			},
			content: '검색결과 예시입니다',
			category: 'question',
			commentCount: 10,
			createdAt: '2022-08-11T16:09',
			views: 11,
			isLike: true,
			likeCount: 10,
		},
		{
			id: 4,
			title: '검색결과1',
			author: {
				name: '샐리',
				avatarUrl: '',
			},
			content: '검색결과 예시입니다',
			category: 'question',
			commentCount: 10,
			createdAt: '2022-08-11T16:09',
			views: 11,
			isLike: true,
			likeCount: 10,
		},
		{
			id: 5,
			title: '검색결과1',
			author: {
				name: '샐리',
				avatarUrl: '',
			},
			content: '검색결과 예시입니다',
			category: 'question',
			commentCount: 10,
			createdAt: '2022-08-11T16:09',
			views: 11,
			isLike: true,
			likeCount: 10,
		},
	],
};
