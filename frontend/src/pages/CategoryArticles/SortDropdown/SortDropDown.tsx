import { keyframes } from '@emotion/react';
import styled from '@emotion/styled';
import { useState } from 'react';
import { MdOutlineKeyboardArrowDown } from 'react-icons/md';

const slideDown = keyframes`
	0% { 
		opacity: 0;
		transform: translateY(-60px);
	}

	100% {
		opacity: 1;
		transform: translateY(0);
	}

`;
const SortBox = styled.div`
	display: flex;
	align-items: center;
	justify-content: center;
	color: ${({ theme }) => theme.colors.BLACK_600};
	cursor: pointer;
`;

const DropdownBox = styled.ul`
	position: absolute;
	top: ${({ theme }) => theme.size.SIZE_016};
	display: flex;
	flex-direction: column;
	align-items: center;
	box-shadow: 0 8px 24px ${({ theme }) => theme.boxShadows.secondary};
	border-bottom-left-radius: ${({ theme }) => theme.size.SIZE_006};
	border-bottom-right-radius: ${({ theme }) => theme.size.SIZE_006};
`;

const DropdownItem = styled.li<{ idx: number }>`
	color: ${({ theme }) => theme.colors.BLACK_600};
	padding: ${({ theme }) => theme.size.SIZE_008} ${({ theme }) => theme.size.SIZE_012}
		${({ theme }) => theme.size.SIZE_008} 0;
	opacity: 0;
	cursor: pointer;
	text-align: center;
	&:hover {
		color: ${({ theme }) => theme.colors.PURPLE_500};
	}
	overflow: hidden;
	border-bottom: 1px solid ${({ theme }) => theme.colors.GRAY_500};
	animation: ${slideDown} 0.3s ease-in-out ${({ idx }) => `${idx * 60}ms`};
	animation-fill-mode: forwards;
	transform-origin: top center;
`;

const Container = styled.div`
	position: relative;
	display: flex;
	flex-direction: column;
	font-size: ${({ theme }) => theme.size.SIZE_012};
`;

const ArrowDown = styled(MdOutlineKeyboardArrowDown)``;

const SortList = ['최신순', '추천순', '조회순'];

const SortDropdown = () => {
	const [onDropdown, setOnDropdown] = useState(false);
	const [selectedOption, setSelectedOption] = useState('최신순');

	const onClickDropdownItem = (sort: string) => {
		setSelectedOption(sort);
		setOnDropdown(false);
	};

	console.log('load');

	return (
		<Container>
			<SortBox
				onClick={() => {
					setOnDropdown((prevOnDropdown) => !prevOnDropdown);
				}}
			>
				<div>{selectedOption}</div>
				<ArrowDown />
			</SortBox>

			{onDropdown && (
				<DropdownBox>
					{SortList.map((sort, idx) => (
						<DropdownItem key={idx} idx={idx} onClick={() => onClickDropdownItem(sort)}>
							{sort}
						</DropdownItem>
					))}
				</DropdownBox>
			)}
		</Container>
	);
};

export default SortDropdown;
