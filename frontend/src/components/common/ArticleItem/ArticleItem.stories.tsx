import { Meta, Story } from '@storybook/react';
import ArticleItem from '@/components/common/ArticleItem/ArticleItem';

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

const Template: Story<{ category: string }> = (args) => <ArticleItem {...args} />;

export const DiscussionArticleItem = Template.bind({});

DiscussionArticleItem.args = {
	category: 'discussion',
};

export const ErrorArticleItem = Template.bind({});

ErrorArticleItem.args = {
	category: 'question',
};
