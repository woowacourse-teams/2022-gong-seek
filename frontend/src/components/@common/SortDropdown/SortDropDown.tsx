import { useState } from 'react';

import * as S from '@/components/@common/SortDropdown/SortDropdown.styles';

export interface SortPropDownProps {
	sortList: string[];
	sortIndex: string;
	setSortIndex: React.Dispatch<React.SetStateAction<string>>;
}

const SortDropdown = ({ sortList, sortIndex, setSortIndex }: SortPropDownProps) => {
	const [onDropdown, setOnDropdown] = useState(false);

	const handleClickSortBox = () => {
		setOnDropdown((prevOnDropdown) => !prevOnDropdown);
	};

	const handleClickDropdownItem = (sort: string) => {
		setSortIndex(sort);
		setOnDropdown(false);
	};

	return (
		<S.Container>
			<S.SortBox onClick={handleClickSortBox}>
				<div>{sortIndex}</div>
				<S.ArrowDown />
			</S.SortBox>

			{onDropdown && (
				<S.DropdownBox>
					{sortList.map((sort, idx) => (
						<S.DropdownItem key={idx} idx={idx} onClick={() => handleClickDropdownItem(sort)}>
							{sort}
						</S.DropdownItem>
					))}
				</S.DropdownBox>
			)}
		</S.Container>
	);
};

export default SortDropdown;
