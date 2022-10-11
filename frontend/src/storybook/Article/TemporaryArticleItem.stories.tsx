import TemporaryArticleItem, {
	TemporaryArticleItemProps,
} from '@/pages/TemporaryArticles/TemporaryArticleItem/TemporaryArticleItem';
import { action } from '@storybook/addon-actions';
import { Meta, Story } from '@storybook/react';

export default {
	title: 'Article/TemporaryArticleItem',
	component: TemporaryArticleItem,
	decorators: [
		(Story) => (
			<div style={{ width: '320px' }}>
				<Story />
			</div>
		),
	],
} as Meta;

const Template: Story<TemporaryArticleItemProps> = (args) => <TemporaryArticleItem {...args} />;

export const Default = Template.bind({});

Default.args = {
	article: {
		title:
			'글 상세페이지에서의 글 제목이 들어가는 곳, 글 제목이 2줄 이상이 넘어갔을때 어떻게 처리할 것인지 처리하기 위한 예시 문장입니다',
		createAt: '2022-08-11T13:34:11',
		category: 'question',
	},
	onClick: () => {
		action('clicked');
	},
};
