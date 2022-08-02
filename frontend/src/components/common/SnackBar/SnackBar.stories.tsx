import SnackBar from '@/components/common/SnackBar/SnackBar';
import { Meta, Story } from '@storybook/react';

const modalRoot = document.createElement('div');
modalRoot.setAttribute('id', 'snack-bar');
document.body.append(modalRoot);

export default {
	title: 'common/SnackBar',
	component: SnackBar,
	decorators: [
		(Story) => (
			<div style={{ width: '320px' }}>
				<Story />
			</div>
		),
	],
} as Meta;

const Template: Story = () => <SnackBar  />;

export const DefaultMenuSlider = Template.bind({});

