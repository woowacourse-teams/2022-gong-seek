import SortDropdown, {
	SortPropDownProps,
} from '@/pages/CategoryArticles/SortDropdown/SortDropDown';
import { action } from '@storybook/addon-actions';
import { Meta, Story } from '@storybook/react';

export default {
	title: 'components/SortDropdown',
	component: SortDropdown,
	decorators: [
		(Story) => (
			<div>
				<Story />
			</div>
		),
	],
} as Meta;

const Template: Story<SortPropDownProps> = (args) => <SortDropdown {...args} />;

export const DefaultSortDropdown = Template.bind({});
DefaultSortDropdown.args = {
	sortIndex: '최신순',
	setSortIndex: () => action('setSortIndex'),
};
